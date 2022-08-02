package es.brouse.auctionhouse.loader.commands;

import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public abstract class ConsoleCommand extends BaseCommand {
    //Is command for the console, it has all the perms, so we don't have to check them
    @Override
    public String getPermission() {
        return "*";
    }

    @Override
    public void addSubCommand(BaseCommand subCommand) {
        if (!(subCommand instanceof ConsoleCommand))
            throw new IllegalStateException("This command only accepts ConsoleCommand subcommands");
        subcommands.add(subCommand);
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        //Check if the sender is a player
        if (sender instanceof Player) {
            sender.sendMessage("This command is not for players");
            return false;
        }

        //If there's any subcommand, try to handle it
        if (!getSubcommands().isEmpty()) {
            //If there's no more args, show the player the info
            if (args.length == 0) {
                execute(sender, command, label, args);
                return false;
            }
            /*If we found a subcommand we'll execute it again the 'onCommand()' but in the subcommand.
            If not, we'll execute the 'execute()' to show the command info*/
            return getSubcommands().stream().filter(baseCommand -> baseCommand.getName().equals(args[0])).findFirst()
                    .map(baseCommand -> baseCommand.onCommand(sender, command, label, removeFirst(args)))
                    .orElseGet(() -> execute(sender, command, label, args));
        }else {
            //Finally, we found the command, so we'll execute it
            return execute(sender, command, label, removeFirst(args));
        }
    }

    private String[] removeFirst(String[] args) {
        String[] newArgs;
        try {
            newArgs = Arrays.copyOfRange(args, 1, args.length);
        }catch (Exception exception) {
            newArgs = new String[0];
        }
        return newArgs;
    }
}
