package es.brouse.auctionhouse.gui;

import es.brouse.auctionhouse.gui.buttons.MenuButtons;
import es.brouse.auctionhouse.inventory.GUI;
import es.brouse.auctionhouse.utils.builders.ItemBuilder;
import org.bukkit.Material;

public class AHBetMenu extends GUI {
    public AHBetMenu() {
        super(9, "messages.menu.title");
        setBackground(ItemBuilder.of(Material.LIGHT_GRAY_STAINED_GLASS_PANE).build());
        setFixed(true);

        //Set up the section items
        //Create GUI
        setButton(1, MenuButtons.addItemMenu());
        //List GUI
        setButton(2, MenuButtons.betMenu());
        //TODO ADM Utils
        //setButton(3, );
        //Exit
        setButton(8, MenuButtons.close());
    }
}
