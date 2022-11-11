package es.brouse.auctionhouse.inventory;

import com.google.common.collect.Maps;
import es.brouse.auctionhouse.utils.Pageable;
import es.brouse.auctionhouse.utils.builders.GUIButtonBuilder;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.function.Consumer;

public class PagedGUI extends GUI implements InventoryHolder, Pageable<GUIButton>{
    //Pageable fields
    private final Map<Integer, Page<GUIButton>> pages;
    @Getter private int currentPage;

    private int nextButtonSlot, prevButtonSlot;
    private GUIButton nextButton, prevButton;
    protected final SlotRestrictive slotRestrictive = new SlotRestrictive();

    public PagedGUI(int size, String key) {
        super(size, key);

        this.currentPage = 1;
        this.pages = Maps.newHashMap();
    }

    @Override
    public boolean nextPage() {
        if (!pages.containsKey(currentPage + 1)) return false;

        currentPage++;
        return true;
    }

    @Override
    public boolean previousPage() {
        if (currentPage == 1) return false;

        currentPage--;
        return true;
    }

    @Override
    public void addPage(int pageIndex, GUIButton[] page) {
        pages.put(pageIndex, new Page<>(super.size - slotRestrictive.getSlots().size(), page));
    }

    @Override
    public boolean removePage(int pageIndex) {
        if (currentPage != pageIndex) {
            pages.remove(pageIndex);
            return true;
        }
        return false;
    }

    @Override
    public Page<GUIButton> getPage(int pageSize) {
        return pages.getOrDefault(pageSize, new Page<>(size));
    }

    @Override
    public @NonNull Inventory getInventory() {

        loadCurrentPage();

        setButton(nextButtonSlot, nextButton);
        setButton(prevButtonSlot, prevButton);

        return super.getInventory();
    }

    /**
     * Set the previous button into the given inventory. If the
     * method isn't called the inventory won't have previous button
     * @param slot inventory slot
     */
    public void setPrevButton(int slot, ItemStack button, Consumer<InventoryClickEvent> event) {
        if (prevButtonSlot != -1)
            removeButton(prevButtonSlot);

        prevButton = prevButton(button, event);
        prevButtonSlot = slot;
    }

    /**
     * Set the next button into the given inventory. If the
     * method isn't called the inventory won't have next button
     * @param slot inventory slot
     */
    public void setNextButton(int slot, ItemStack button, Consumer<InventoryClickEvent> event) {
        if (nextButtonSlot != -1)
            removeButton(nextButtonSlot);

        nextButtonSlot = slot;
        nextButton = nextButton(button, event);
    }

    /**
     * Get the previous button {@link GUIButton}
     * @param button itemStack of the button
     * @param eventConsumer event triggered by the button
     * @return the GUI previous button
     */
    private GUIButton prevButton(ItemStack button, Consumer<InventoryClickEvent> eventConsumer) {
        return GUIButtonBuilder.create().button(button).clickEvent(eventConsumer).build();
    }

    /**
     * Get the previous button {@link GUIButton}
     * @param button itemStack of the button
     * @return the GUI next button
     */
    private GUIButton nextButton(ItemStack button, Consumer<InventoryClickEvent> eventConsumer) {
        return GUIButtonBuilder.create().button(button).clickEvent(eventConsumer).build();
    }

    /**
     * Load the currentPage into the given inventory
     */
    private void loadCurrentPage() {
        //Clear all the buttons
        for (Integer pos : getButtons().keySet()) {
            removeButton(pos);
        }
        //Add the page content to the buttons
        for (Map.Entry<Integer, GUIButton> entry : getPage(getCurrentPage()).getEntries().entrySet()) {
            setButton(entry.getKey(), entry.getValue());
        }
    }
}
