package es.brouse.auctionhouse.gui;

import es.brouse.auctionhouse.AuctionHouse;
import es.brouse.auctionhouse.entities.AuctionHouseItem;
import es.brouse.auctionhouse.events.AHItemBet;
import es.brouse.auctionhouse.gui.buttons.BetButtons;
import es.brouse.auctionhouse.inventory.GUI;
import es.brouse.auctionhouse.nbt.NBTHelper;
import es.brouse.auctionhouse.serialize.EntitySerializer;
import es.brouse.auctionhouse.serialize.serializers.Serializer;
import es.brouse.auctionhouse.utils.builders.GUIButtonBuilder;
import es.brouse.auctionhouse.utils.builders.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BetGUI extends GUI {
    private final AuctionHouseItem item;

    public BetGUI(ItemStack itemStack) {
        super(9, "messages.betgui.title");

        item = getEntity(itemStack);
        setBackground(ItemBuilder.of(Material.LIGHT_GRAY_STAINED_GLASS_PANE).build());

        setButton(0, GUIButtonBuilder.create().button(itemStack).build());
        setButton(2, BetButtons.bet1(event -> {
            bet(((Player) event.getWhoClicked()), item, 1d);
            event.getWhoClicked().closeInventory();
        }));
        setButton(3, BetButtons.bet2(event -> {
            bet(((Player) event.getWhoClicked()), item, 100d);
            event.getWhoClicked().closeInventory();
        }));
        setButton(4, BetButtons.bet3(event -> {
            bet(((Player) event.getWhoClicked()), item, 1_000d);
            event.getWhoClicked().closeInventory();
        }));

        //If ProtocolLib is enabled add the SignGUI
        if (AuctionHouse.getSignGUI() != null) {
            setButton(5, BetButtons.betCustom(event -> new CustomBetGUI()
                    .open(((Player) event.getWhoClicked()), amount ->
                            bet(((Player) event.getWhoClicked()), item, amount))
            ));
        }

        setButton(8, BetButtons.closeInventory(event -> event.getWhoClicked().closeInventory()));
    }

    private AuctionHouseItem getEntity(ItemStack itemStack) {
        String bet_id = NBTHelper.getKey(itemStack, "bet_id");
        AuctionHouseItem item = AuctionHouseItem.builder().identifier(bet_id).build();
        Serializer<AuctionHouseItem> serializer = EntitySerializer.getSerializer(item);

        return serializer.getEntity();
    }

    private void bet(Player player, AuctionHouseItem item, double priceAdd) {
        //Call AHItemBet event
        AHItemBet itemBet = new AHItemBet(item, player, priceAdd);
        Bukkit.getPluginManager().callEvent(itemBet);
    }
}
