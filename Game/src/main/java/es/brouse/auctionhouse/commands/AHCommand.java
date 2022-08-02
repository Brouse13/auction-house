package es.brouse.auctionhouse.commands;

import es.brouse.auctionhouse.gui.AHGUI;
import es.brouse.auctionhouse.loader.commands.PlayerCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AHCommand extends PlayerCommand {
    @Override
    public String getName() {
        return "ah";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        ((Player) sender).openInventory(new AHGUI().getInventory());
        return true;
    }
}
