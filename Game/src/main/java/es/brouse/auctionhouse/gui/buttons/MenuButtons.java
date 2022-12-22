package es.brouse.auctionhouse.gui.buttons;

import es.brouse.auctionhouse.gui.AHGUI;
import es.brouse.auctionhouse.inventory.GUIButton;
import es.brouse.auctionhouse.translator.Translator;
import es.brouse.auctionhouse.utils.builders.GUIButtonBuilder;
import es.brouse.auctionhouse.utils.builders.ItemBuilder;
import org.bukkit.Material;

public class MenuButtons {
    public static GUIButton close() {
        return GUIButtonBuilder.create()
                .button(ItemBuilder.of(Material.ARROW)
                        .displayName(Translator.getString("messages.menu.close.name"))
                        .lore(Translator.getStringList("messages.menu.close.lore"))
                        .build())
                .clickEvent(event -> event.getWhoClicked().closeInventory())
                .build();
    }

    public static GUIButton addItemMenu() {
        return GUIButtonBuilder.create()
                .button(ItemBuilder.of(Material.GREEN_WOOL)
                        .displayName(Translator.getString("messages.menu.add.name"))
                        .lore(Translator.getStringList("messages.menu.add.lore"))
                        .build())
                .clickEvent(event -> event.getWhoClicked().closeInventory())
                .build();
    }

    public static GUIButton betMenu() {
        return GUIButtonBuilder.create()
                .button(ItemBuilder.of(Material.HOPPER)
                        .displayName(Translator.getString("messages.menu.bet.name"))
                        .lore(Translator.getStringList("messages.menu.bet.lore"))
                        .build())
                .clickEvent(event -> event.getWhoClicked().openInventory(new AHGUI().getInventory()))
                .build();
    }
}
