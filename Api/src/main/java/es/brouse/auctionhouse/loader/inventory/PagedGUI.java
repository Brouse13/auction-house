package es.brouse.auctionhouse.loader.inventory;

import com.google.common.collect.Maps;
import es.brouse.auctionhouse.loader.utils.Pageable;
import es.brouse.auctionhouse.loader.utils.builders.GUIButtonBuilder;
import es.brouse.auctionhouse.loader.utils.builders.ItemBuilder;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PagedGUI extends Pageable<ItemStack> implements InventoryHolder {
    private final String title;
    private final int size;
    private ItemStack background = new ItemStack(Material.AIR);
    private final Map<Integer, GUIButton> buttons = Maps.newHashMap();

    private int prevButtonSlot;
    private int nextButtonSlot;


    @Getter private final SlotRestrictive slotRestrictive = new SlotRestrictive();

    /**
     * Create a new PagedGUI with a given title and size
     * @param size inventory size
     * @param name inventory title
     */
    public PagedGUI(int size, String name) {
        super(28);
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
     * Set the previous button into the given inventory. If the
     * method isn't called the inventory won't have previous button
     * @param slot inventory slot
     */
    public void setPrevButton(int slot) {
        if (prevButtonSlot != -1)
            removeButton(prevButtonSlot);

        setButton(slot, prevButton());
        prevButtonSlot = slot;
    }

    /**
     * Set the next button into the given inventory. If the
     * method isn't called the inventory won't have next button
     * @param slot inventory slot
     */
    public void setNextButton(int slot) {
        if (nextButtonSlot != -1)
            removeButton(nextButtonSlot);

        setButton(slot, nextButton());
        nextButtonSlot = slot;
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
     * Get the inventory that corresponds to that {@link InventoryHolder}
     * @return the {@link Inventory} instance
     */
    @Override
    public @NonNull Inventory getInventory() {
        //Create the inventory and handle '%page' translation
        Inventory inventory = Bukkit.createInventory(this, size,
                title.contains("%page%") ? title.replaceAll("%page%", String.valueOf(getCurrentPage())) : title);

        //Set the inventory background material
        for (int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, background);

        //Add the buttons to the GUI
        buttons.forEach((slot, button) -> inventory.setItem(slot, button.getButton()));

        //Add the page content
        loadCurrentPage(inventory);

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
     * @param inventory inventory to fill
     */
    private void loadCurrentPage(Inventory inventory) {
        Set<Integer> slots = slotRestrictive.getSlots();

        if (!slotRestrictive.isRestrict())
            for (int i = 0; i < size - 9; i++) slots.add(i);

        //Clear last previous page elements
        for (Integer slot : slots)
            inventory.clear(slot);

        //Fill the new page
        List<ItemStack> pageContent = getPage(getCurrentPage());
        int i = 0;
        for (Integer slot : slots) {
            try {
                setButton(slot,  GUIButtonBuilder.create()
                        .button(ItemBuilder.of(pageContent.get(i)).build())
                        .clickEvent(event -> System.out.println("Clicked "+ event.getClick())).build());
            }catch (IndexOutOfBoundsException exception) {
                //If the pageContent is lower to the slot stop filling
                break;
            }
            i++;
        }
    }

    /**
     * Get the previous button {@link GUIButton}
     * @return the GUI previous button
     */
    public GUIButton prevButton() {
        return GUIButtonBuilder.create()
                .button(new ItemStack(Material.ARROW))
                .clickEvent(event -> {
                    if (previousPage()) openPage(event.getWhoClicked());
                }).build();
    }

    /**
     * Get the previous button {@link GUIButton}
     * @return the GUI next button
     */
    public GUIButton nextButton() {
        return GUIButtonBuilder.create()
                .button(new ItemStack(Material.ARROW))
                .clickEvent(event -> {
                    if (nextPage()) openPage(event.getWhoClicked());
                }).build();
    }
}
