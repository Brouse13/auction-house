package es.brouse.auctionhouse.loader.inventory;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import es.brouse.auctionhouse.loader.config.YamlConfig;
import es.brouse.auctionhouse.loader.translator.Translator;
import es.brouse.auctionhouse.loader.utils.Pageable;
import es.brouse.auctionhouse.loader.utils.builders.GUIButtonBuilder;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class PagedGUI extends Pageable<GUIButton> implements InventoryHolder {
    @Getter private final String title;
    private final int size;
    private ItemStack background = new ItemStack(Material.AIR);
    private final Map<Integer, GUIButton> buttons = Maps.newHashMap();

    private int prevButtonSlot;
    private ItemStack prevButton;
    private int nextButtonSlot;
    private ItemStack nextButton;

    //Class to manage in a PagedGUI inventory witch slots won't be moved
    @Getter private final SlotRestrictive slotRestrictive = new SlotRestrictive();

    /**
     * Create a new PagedGUI with a given title and size
     * @param size inventory size
     * @param key inventory title
     */
    public PagedGUI(int size, String key) {
        super(28);
        this.title = Translator.getString(key, new YamlConfig().getLang());
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
     * Get all the GUIButtons o the PagedGUI in an immutable map
     * @return the inventory buttons
     */
    public Map<Integer, GUIButton> getButtons() {
        return ImmutableMap.copyOf(buttons);
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

    /**
     * Set the previous button into the given inventory. If the
     * method isn't called the inventory won't have previous button
     * @param slot inventory slot
     */
    public void setPrevButton(int slot, ItemStack button) {
        if (prevButtonSlot != -1)
            removeButton(prevButtonSlot);

        setButton(slot, prevButton(button));
        prevButtonSlot = slot;
        prevButton = button;
    }

    /**
     * Set the next button into the given inventory. If the
     * method isn't called the inventory won't have next button
     * @param slot inventory slot
     */
    public void setNextButton(int slot, ItemStack button) {
        if (nextButtonSlot != -1)
            removeButton(nextButtonSlot);

        setButton(slot, nextButton(button));
        nextButtonSlot = slot;
        nextButton = button;
    }

    /**
     * Get the inventory that corresponds to that {@link InventoryHolder}
     * @return the {@link Inventory} instance
     */
    @Override
    public @NonNull Inventory getInventory() {
        //Create the inventory and handle '%page' translation
        Inventory inventory = Bukkit.createInventory(this, size, getTitle());

        //Set the inventory background material
        for (int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, background);

        //Add the pageContent to the buttons
        loadCurrentPage(inventory.getSize(), getPage(getCurrentPage()));

        //Add the buttons to the inv
        buttons.put(nextButtonSlot, prevButton(prevButton));
        buttons.put(nextButtonSlot, nextButton(nextButton));

        //Add the buttons to the GUI
        buttons.forEach((slot, button) -> inventory.setItem(slot, button.getButton()));


        //Add all the buttons to the inventory
        for (Integer slot : buttons.keySet())
            inventory.setItem(slot, buttons.get(slot).getButton());


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

    /**
     * Load the currentPage into the given inventory
     * @param size inventory size
     *
     */
    public void loadCurrentPage(int size, List<GUIButton> pages) {
        //Add the page content to the buttons
        for (int i = 0; i < size; i++) {
            try {
                setButton(i, pages.get(i));
            }catch (IndexOutOfBoundsException exception) {
                //Stop filling when find exception
                break;
            }
        }
    }

    /**
     * Get the previous button {@link GUIButton}
     * @param button itemStack of the button
     * @return the GUI previous button
     */
    public GUIButton prevButton(ItemStack button) {
        return GUIButtonBuilder.create().button(button)
                .clickEvent(event -> {
                    if (previousPage())
                        openPage(event.getWhoClicked());
                    event.setCancelled(true);
                }).build();
    }

    /**
     * Get the previous button {@link GUIButton}
     * @param button itemStack of the button
     * @return the GUI next button
     */
    public GUIButton nextButton(ItemStack button) {
        return GUIButtonBuilder.create().button(button)
                .clickEvent(event -> {
                    if (nextPage())
                        openPage(event.getWhoClicked());
                    event.setCancelled(true);
                }).build();
    }
}
