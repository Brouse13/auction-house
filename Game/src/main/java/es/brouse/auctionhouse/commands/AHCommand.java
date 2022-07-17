package es.brouse.auctionhouse.commands;

import es.brouse.auctionhouse.loader.commands.PlayerCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class AHCommand extends PlayerCommand {
    //TODO NOT DONE
    public AHCommand() {

    }

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
        return false;
    }
}
