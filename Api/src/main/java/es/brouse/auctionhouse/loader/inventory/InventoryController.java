package es.brouse.auctionhouse.loader.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryController implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof GUI)) return;

        GUI holder = (GUI) event.getInventory().getHolder();

        if (holder.isFixed()) event.setCancelled(true);

        if (holder.getButtons().containsKey(event.getSlot()))
            holder.getButtons().get(event.getSlot()).clickEvent().accept(event);
    }
}
