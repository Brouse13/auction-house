package es.brouse.auctionhouse.gui;

import es.brouse.auctionhouse.AuctionHouse;
import es.brouse.auctionhouse.loader.inventory.GUI;
import es.brouse.auctionhouse.loader.translator.Translator;
import es.brouse.auctionhouse.loader.utils.builders.GUIButtonBuilder;
import es.brouse.auctionhouse.loader.utils.builders.ItemBuilder;
import es.brouse.auctionhouse.section.AHSection;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class AHGUI extends GUI {
    public AHGUI() {
        super(9, Translator.getString("messages.gui.ah.title", AuctionHouse.getSettings().getLang()));
        setBackground(ItemBuilder.of(Material.LIGHT_GRAY_STAINED_GLASS_PANE).build());
        setFixed(true);

        //Set up the section items
        setButton(1, GUIButtonBuilder.create()
                .button(ItemBuilder.of(Material.APPLE).build())
                .clickEvent(openSectionGUI(AHSection.FOOD)).build());
        setButton(2, GUIButtonBuilder.create()
                .button(ItemBuilder.of(Material.DIAMOND_CHESTPLATE).build())
                .clickEvent(openSectionGUI(AHSection.ARMOUR)).build());
        setButton(3, GUIButtonBuilder.create()
                .button(ItemBuilder.of(Material.DIAMOND_SWORD).build())
                .clickEvent(openSectionGUI(AHSection.WEAPONS)).build());
        setButton(4, GUIButtonBuilder.create()
                .button(ItemBuilder.of(Material.BRICKS).build())
                .clickEvent(openSectionGUI(AHSection.BLOCKS)).build());
        setButton(5, GUIButtonBuilder.create()
                .button(ItemBuilder.of(Material.REDSTONE).build())
                .clickEvent(openSectionGUI(AHSection.REDSTONE)).build());
        setButton(6, GUIButtonBuilder.create()
                .button(ItemBuilder.of(Material.ENCHANTED_BOOK).build())
                .clickEvent(openSectionGUI(AHSection.BOOKS)).build());
        setButton(7, GUIButtonBuilder.create()
                .button(ItemBuilder.of(Material.GLASS_BOTTLE).build())
                .clickEvent(openSectionGUI(AHSection.POTIONS)).build());
    }

    private Consumer<InventoryClickEvent> openSectionGUI(AHSection section) {
        return event -> System.out.println(section);
    }
}
