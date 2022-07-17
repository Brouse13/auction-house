package es.brouse.auctionhouse.loader.inventory;

import com.google.common.collect.Maps;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class GUI implements InventoryHolder {
    private final String title;
    private final int size;
    private ItemStack background = new ItemStack(Material.AIR);
    private final Map<Integer, GUIButton> buttons = Maps.newHashMap();

    /**
     * Create a new PagedGUI with a given title and size
     * @param size inventory size
     * @param name inventory title
     */
    public GUI(int size, String name) {
        this.title = ChatColor.translateAlternateColorCodes('&', name);
        this.size = size;
    }

    /**
     * Set the background material to the give on {@param background}
     * @param background background {@link ItemStack}
     */
    public void setBackground(ItemStack background) {
        this.background = background;
    }

    /**
     * Add to the inventory the {@param button} on the {@param slot} slot
     * @param slot inventory slot
     * @param button button to set
     */
    public void setButton(int slot, GUIButton button) {
        buttons.put(slot, button);
    }

    /**
     * Remove from the inventory the button on the {@param slot} slot
     * @param slot inventory slot
     */
    public void removeButton(int slot) {
        buttons.remove(slot);
    }

    @Override
    public @NonNull Inventory getInventory() {
        //Create the inventory
        Inventory inventory = Bukkit.createInventory(this, size, title);

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
    private void openPage(HumanEntity holder) {
        holder.closeInventory();
        holder.openInventory(getInventory());
    }
}
