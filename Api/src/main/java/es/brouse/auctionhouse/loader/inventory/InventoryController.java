package es.brouse.auctionhouse.loader.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryController implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void guiHolder(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof GUI)) return;

        GUI holder = (GUI) event.getInventory().getHolder();

        if (holder.isFixed()) event.setCancelled(true);

        if (holder.getButtons().containsKey(event.getSlot()))
            holder.getButtons().get(event.getSlot()).clickEvent().accept(event);
    }

    @EventHandler(ignoreCancelled = true)
    public void pagedGuiHolder(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof PagedGUI)) return;

        PagedGUI holder = (PagedGUI) event.getInventory().getHolder();

        SlotRestrictive slotRestrictive = holder.getSlotRestrictive();
        if (slotRestrictive.isRestrict())
            if (slotRestrictive.getSlots().contains(event.getSlot()))
                event.setCancelled(true);

        GUIButton button = holder.getButtons().get(event.getSlot());
        if (button != null)
            button.clickEvent().accept(event);
    }
}
