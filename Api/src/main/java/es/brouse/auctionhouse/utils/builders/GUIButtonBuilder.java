package es.brouse.auctionhouse.utils.builders;

import es.brouse.auctionhouse.inventory.GUIButton;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class GUIButtonBuilder {
    private ItemStack button = new ItemStack(Material.AIR);
    private Consumer<InventoryClickEvent> buttonEvent = event -> {};

    /**
     * Private constructor to use the builder pattern
     */
    protected GUIButtonBuilder() {

    }

    /**
     * Create a new empty {@link GUIButtonBuilder}
     * @return the builder instance
     */
    public static GUIButtonBuilder create() {
        return new GUIButtonBuilder();
    }

    /**
     * Set the button material
     * @param button button material
     * @return the builder instance
     */
    public GUIButtonBuilder button(ItemStack button) {
        this.button = button;
        return this;
    }

    /**
     * Set the button clickEvent
     * @param clickEvent button clickEvent
     * @return the builder instance
     */
    public GUIButtonBuilder clickEvent(Consumer<InventoryClickEvent> clickEvent) {
        this.buttonEvent = clickEvent;
        return this;
    }

    /**
     * Create a new {@link GUIButton} with the params on the builder
     * @return the GUIButton
     */
    public GUIButton build() {
        return new GUIButton() {
            @Override
            public @NonNull ItemStack getButton() {
                return button;
            }

            @Override
            public @NonNull Consumer<InventoryClickEvent> clickEvent() {
                return buttonEvent;
            }
        };
    }
}
