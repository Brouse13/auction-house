package es.brouse.auctionhouse.inventory;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.function.BiPredicate;

public class SignGUI {
    private static JavaPlugin plugin;
    private static final Map<Player, Menu> MENUS = Maps.newHashMap();

    public static void init(JavaPlugin plugin) {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("ProtocolLib"))
            return;

        if (SignGUI.plugin != null)
            throw new IllegalStateException("Logger was already initiated");

        SignGUI.plugin = plugin;
        scheduler();
    }

    public static boolean isEnabled() {
        return plugin != null;
    }

    private static void scheduler() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Client.UPDATE_SIGN) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();

                Menu menu = MENUS.remove(player);

                if (menu == null) return;

                event.setCancelled(true);

                boolean success = menu.response.test(player, event.getPacket().getStringArrays().read(0));

                if (!success) {
                    Bukkit.getScheduler().runTaskLater(plugin, () -> menu.open(player), 2L);
                }

                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    if (player.isOnline()) {
                        Location location = menu.position.toLocation(player.getWorld());
                        player.sendBlockChange(location, location.getBlock().getBlockData());
                    }
                }, 2L);
            }
        });
    }

    @Builder
    @AllArgsConstructor
    public static class Menu {
        private final BiPredicate<Player, String[]> response;
        private BlockPosition position;

        public void open(Player player) {
            if (!player.isOnline()) return;

            Location location = player.getLocation();
            ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

            this.position = new BlockPosition(location.toVector().add(new Vector(0, 2, 0)));

            //https://wiki.vg/Protocol#Block_Update
            PacketContainer blockChange = protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
            blockChange.getBlockPositionModifier().write(0, position);
            blockChange.getBlockData().write(0, WrappedBlockData.createData(Material.OAK_SIGN));

            //https://wiki.vg/Protocol#Open_Sign_Editor
            PacketContainer openSignEditor = protocolManager.createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
            openSignEditor.getBlockPositionModifier().write(0, position);


            //Send the packets
            protocolManager.sendServerPacket(player, blockChange);
            protocolManager.sendServerPacket(player, openSignEditor);

            MENUS.put(player, this);
        }
    }
}
