package es.brouse.auctionhouse.gui.buttons;

import es.brouse.auctionhouse.entities.AuctionHouseItem;
import es.brouse.auctionhouse.inventory.GUIButton;
import es.brouse.auctionhouse.nbt.NBTHelper;
import es.brouse.auctionhouse.translator.Translator;
import es.brouse.auctionhouse.utils.builders.GUIButtonBuilder;
import es.brouse.auctionhouse.utils.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class SectionButtons {

    /**
     * Create a GUIButton of the SectionGUI that will represent each AuctionHouseItem in the page
     * @param item item to represent
     * @param event event to trigger on click
     * @return the created GUIButton
     */
    public static GUIButton sectionButton(AuctionHouseItem item, Consumer<InventoryClickEvent> event) {
        //Create the itemStack
        ItemStack itemStack = ItemBuilder.of(Material.getMaterial(item.getMaterial()))
                .displayName(Translator.getString("messages.sectiongui.item.name",
                        item.getMaterial()))
                .lore(Translator.getStringList("messages.sectiongui.item.lore",
                        item.getOwner(), item.getPrice())).build();
        //Set the id to recognize the AuctionHouseItem
        NBTHelper.setKey(itemStack, "bet_id", item.getIdentifier());

        return GUIButtonBuilder.create().button(itemStack).clickEvent(event).build();
    }
}
