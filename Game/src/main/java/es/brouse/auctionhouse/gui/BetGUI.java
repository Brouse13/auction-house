package es.brouse.auctionhouse.gui;

import es.brouse.auctionhouse.loader.inventory.GUI;
import es.brouse.auctionhouse.loader.utils.builders.GUIButtonBuilder;
import es.brouse.auctionhouse.loader.utils.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BetGUI extends GUI {
    public BetGUI(ItemStack itemStack) {
        super(9, "messages.betgui.title");
        setBackground(ItemBuilder.of(Material.LIGHT_GRAY_STAINED_GLASS_PANE).build());

        setButton(0, GUIButtonBuilder.create().button(itemStack).build());
        setButton(2, BetButtons.bet1(event -> {}));
        setButton(3, BetButtons.bet2(event -> {}));
        setButton(4, BetButtons.bet3(event -> {}));
        setButton(5, BetButtons.betCustom(event -> {}));

        setButton(8, BetButtons.closeInventory(event -> event.getWhoClicked().closeInventory()));
    }
}
