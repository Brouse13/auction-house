package es.brouse.auctionhouse.gui;

import es.brouse.auctionhouse.AuctionHouse;
import es.brouse.auctionhouse.config.YamlConfig;
import es.brouse.auctionhouse.entities.AHSection;
import es.brouse.auctionhouse.inventory.GUIButton;
import es.brouse.auctionhouse.translator.Translator;
import es.brouse.auctionhouse.utils.builders.GUIButtonBuilder;
import es.brouse.auctionhouse.utils.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class AHButtons {
    private static final YamlConfig config = AuctionHouse.getSettings();

    public static GUIButton sectionButton(AHSection section, Consumer<InventoryClickEvent> event) {
        return GUIButtonBuilder.create().clickEvent(event)
                .button(createSection(section)).build();
    }


    private static ItemStack createSection(AHSection section) {
        String key = section.name().toLowerCase();

        //Get the Material of the config of the def material
        Material material = config.getConfig().getDefEnum(
                "ah." + key, Material.class, section.getDefMaterial());

        //Return the GUIButton of the AHSection
        return ItemBuilder.of(material)
                .displayName(Translator.getString("messages.ah." + key + ".name", config.getLang()))
                .lore(Translator.getStringList("messages.ah." + key + ".lore", config.getLang())).build();
    }

}
