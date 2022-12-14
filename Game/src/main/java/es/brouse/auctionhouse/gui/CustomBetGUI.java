package es.brouse.auctionhouse.gui;

import es.brouse.auctionhouse.inventory.SignGUI;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class CustomBetGUI {

    public void open(Player player, Consumer<Integer> action) {
        //If SignGUI hasn't been enabled
        if (!SignGUI.isEnabled()) return;

        SignGUI.Menu.builder().response((target, lines) -> {
            try {
                action.accept(Integer.parseInt(String.join("", lines)));
                return true;
            }catch (Exception exception) {
                exception.printStackTrace();
                return false;
            }
        }).build().open(player);
    }
}
