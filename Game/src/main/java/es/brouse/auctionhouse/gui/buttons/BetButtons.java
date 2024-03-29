package es.brouse.auctionhouse.gui.buttons;

import es.brouse.auctionhouse.AuctionHouse;
import es.brouse.auctionhouse.config.YamlConfig;
import es.brouse.auctionhouse.inventory.GUIButton;
import es.brouse.auctionhouse.translator.Translator;
import es.brouse.auctionhouse.utils.builders.GUIButtonBuilder;
import es.brouse.auctionhouse.utils.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class BetButtons {
    private static final YamlConfig config = AuctionHouse.getSettings();

    /**
     * Get the GUIButton of the bet1 on the BetGUI
     * @param event event to trigger on click
     * @return the created button
     */

    public static GUIButton bet1(Consumer<InventoryClickEvent> event) {
        return getBet("bet1", event);
    }

    /**
     * Get the GUIButton of the bet2 on the BetGUI
     * @param event event to trigger on click
     * @return the created button
     */
    public static GUIButton bet2(Consumer<InventoryClickEvent> event) {
        return getBet("bet2", event);
    }

    /**
     * Get the GUIButton of the bet3 on the BetGUI
     * @param event event to trigger on click
     * @return the created button
     */
    public static GUIButton bet3(Consumer<InventoryClickEvent> event) {
        return getBet("bet3", event);
    }

    /**
     * Get the GUIButton of the betCustom on the BetGUI
     * @param event event to trigger on click
     * @return the created button
     */
    public static GUIButton betCustom(Consumer<InventoryClickEvent> event) {
        return getBet("betCustom", event);
    }

    /**
     * Get the GUIButton of the closeInventory on the BetGUI
     * @param event event to trigger on click
     * @return the created button
     */
    public static GUIButton closeInventory(Consumer<InventoryClickEvent> event) {
        return GUIButtonBuilder.create().button(ItemBuilder.of(Material.BARRIER)
                        .displayName(Translator.getString("messages.betgui.close.name"))
                        .lore(Translator.getStringList("messages.betgui.close.lore"))
                        .build()).clickEvent(event).build();
    }

    private static GUIButton getBet(String betType, Consumer<InventoryClickEvent> event) {
        String betMessage = "messages.betgui." + betType;
        Material material = config.getConfig()
                .getDefEnum("betgui." + betType, Material.class, Material.GOLD_NUGGET);

        return GUIButtonBuilder.create().button(ItemBuilder.of(material)
                .displayName(Translator.getString(betMessage + ".name"))
                .lore(Translator.getStringList(betMessage + ".lore"))
                .build()).clickEvent(event).build();
    }
}
