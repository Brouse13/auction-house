package es.brouse.auctionhouse.gui;

import es.brouse.auctionhouse.loader.entities.AHSection;
import es.brouse.auctionhouse.loader.inventory.GUI;
import es.brouse.auctionhouse.loader.utils.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class AHGUI extends GUI {
    public AHGUI() {
        super(9, "messages.ah.title");
        setBackground(ItemBuilder.of(Material.LIGHT_GRAY_STAINED_GLASS_PANE).build());
        setFixed(true);

        //Set up the section items
        setButton(1, AHButtons.sectionButton(AHSection.FOOD, openSectionGUI(AHSection.FOOD)));
        setButton(2, AHButtons.sectionButton(AHSection.ARMOUR, openSectionGUI(AHSection.ARMOUR)));
        setButton(3, AHButtons.sectionButton(AHSection.WEAPONS, openSectionGUI(AHSection.WEAPONS)));
        setButton(4, AHButtons.sectionButton(AHSection.BLOCKS, openSectionGUI(AHSection.BLOCKS)));
        setButton(5, AHButtons.sectionButton(AHSection.REDSTONE, openSectionGUI(AHSection.REDSTONE)));
        setButton(6, AHButtons.sectionButton(AHSection.BOOKS, openSectionGUI(AHSection.BOOKS)));
        setButton(7, AHButtons.sectionButton(AHSection.POTIONS, openSectionGUI(AHSection.POTIONS)));
    }

    private Consumer<InventoryClickEvent> openSectionGUI(AHSection section) {
        return event -> event.getWhoClicked().openInventory(new SectionGUI(section).getInventory());
    }
}
