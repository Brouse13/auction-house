package es.brouse.auctionhouse.loader.inventory;

import lombok.NonNull;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public interface GUIButton {
    /**
     * Get the material of the button
     * @return the button material
     */
    @NonNull ItemStack getButton();

    /**
     * Get the action that will be triggered when
     * the button is pressed.
     * @return a consumer with the button action
     */
    @NonNull Consumer<InventoryClickEvent> clickEvent();
}
