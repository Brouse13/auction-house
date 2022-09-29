package es.brouse.auctionhouse.gui;

import es.brouse.auctionhouse.AuctionHouse;
import es.brouse.auctionhouse.loader.inventory.GUI;
import es.brouse.auctionhouse.loader.translator.Translator;
import es.brouse.auctionhouse.loader.utils.builders.GUIButtonBuilder;
import es.brouse.auctionhouse.loader.utils.builders.ItemBuilder;
import es.brouse.auctionhouse.section.AHSection;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class AHGUI extends GUI {
    public AHGUI() {
        super(9, Translator.getString("messages.ah.title", AuctionHouse.getSettings().getLang()));
        setBackground(ItemBuilder.of(Material.LIGHT_GRAY_STAINED_GLASS_PANE).build());
        setFixed(true);

        //Set up the section items
        setButton(1, GUIButtonBuilder.create()
                .button(createItem(Material.APPLE, "messages.ah.food"))
                .clickEvent(openSectionGUI(AHSection.FOOD)).build());
        setButton(2, GUIButtonBuilder.create()
                .button(createItem(Material.DIAMOND_CHESTPLATE, "messages.ah.armour"))
                .clickEvent(openSectionGUI(AHSection.ARMOUR)).build());
        setButton(3, GUIButtonBuilder.create()
                .button(createItem(Material.DIAMOND_SWORD, "messages.ah.combat"))
                .clickEvent(openSectionGUI(AHSection.WEAPONS)).build());
        setButton(4, GUIButtonBuilder.create()
                .button(createItem(Material.BRICKS, "messages.ah.building"))
                .clickEvent(openSectionGUI(AHSection.BLOCKS)).build());
        setButton(5, GUIButtonBuilder.create()
                .button(createItem(Material.REDSTONE, "messages.ah.redstone"))
                .clickEvent(openSectionGUI(AHSection.REDSTONE)).build());
        setButton(6, GUIButtonBuilder.create()
                .button(createItem(Material.ENCHANTED_BOOK, "messages.ah.enchantments"))
                .clickEvent(openSectionGUI(AHSection.BOOKS)).build());
        setButton(7, GUIButtonBuilder.create()
                .button(createItem(Material.GLASS_BOTTLE, "messages.ah.potions"))
                .clickEvent(openSectionGUI(AHSection.POTIONS)).build());
    }

    private Consumer<InventoryClickEvent> openSectionGUI(AHSection section) {
        return event -> event.getWhoClicked().openInventory(new SectionGUI(section).getInventory());
    }

    private ItemStack createItem(Material material, String key) {
        Translator.Lang lang = AuctionHouse.getSettings().getLang();
        return ItemBuilder.of(material)
                .displayName(Translator.getString(key + ".name", lang))
                .lore(Translator.getStringList(key + ".lore", lang)).build();
    }
}
