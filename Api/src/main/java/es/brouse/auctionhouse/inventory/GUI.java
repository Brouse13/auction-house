package es.brouse.auctionhouse.inventory;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import es.brouse.auctionhouse.translator.Translator;
import es.brouse.auctionhouse.utils.builders.ItemBuilder;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class GUI implements InventoryHolder {
    @Getter protected final String title;
    protected final int size;
    protected boolean fixed;
    private ItemStack background;
    private final Map<Integer, GUIButton> buttons;

    /**
     * Create a new PagedGUI with a given title and size
     * @param size inventory size
     * @param key inventory title
     */
    public GUI(int size, String key) {
        this.title = Translator.getString(key);
        this.size = size;
        this.fixed = false;
        this.buttons = Maps.newHashMap();
        this.background = ItemBuilder.of(Material.AIR).build();
    }

    /**
     * Set the background material to the give on {@param background}
     * @param background background {@link ItemStack}
     */
    public void setBackground(ItemStack background) {
        this.background = background;
    }

    /**
     * Set if the item inside inventory can be moved otherwise
     * players won't be able to move them.
     * @param fixed If players can move items
     */
    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    /**
     * Add to the inventory the {@param button} on the {@param slot} slot
     * @param slot inventory slot
     * @param button button to set
     */
    public void setButton(int slot, GUIButton button) {
        try {
            buttons.put(slot, button);
        }catch (Exception exception) {
            //Ignore if you can't put the item
        }
    }

    /**
     * Remove from the inventory the button on the {@param slot} slot
     * @param slot inventory slot
     */
    public void removeButton(int slot) {
        buttons.remove(slot);
    }

    /**
     * Get an immutable map with all the buttons on the GUI
     * @return all the GUI buttons
     */
    public Map<Integer, GUIButton> getButtons() {
        return ImmutableMap.copyOf(buttons);
    }

    @Override
    public @NonNull Inventory getInventory() {
        //Create the inventory
        Inventory inventory = Bukkit.createInventory(this, size, getTitle());

        //Set the inventory background material
        for (int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, background);

        //Add the buttons to the GUI
        buttons.forEach((slot, button) -> inventory.setItem(slot, button.getButton()));
        return inventory;

    }

    /**
     * Open the currentPage to the {@param holder}
     * @param holder HumanEntity that click the inventory
     */
    public void refreshPage(HumanEntity holder) {
        holder.closeInventory();
        holder.openInventory(getInventory());
    }
}
