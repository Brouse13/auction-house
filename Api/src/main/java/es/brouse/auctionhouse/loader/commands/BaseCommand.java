package es.brouse.auctionhouse.loader.commands;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Set;

public abstract class BaseCommand implements CommandExecutor {
    protected Set<BaseCommand> subcommands = Sets.newHashSet();

    /**
     * Get the name of the command
     * @return the command name
     */
    public abstract String getName();

    /**
     * Get the permission of the command
     * @return the command permission
     */
    public abstract String getPermission();

    /**
     * Add a new subcommand to the subCommands list
     * @param subCommand subcommand to add
     */
    public void addSubCommand(BaseCommand subCommand) {
        subcommands.add(subCommand);
    }

    /**
     * Get all the subcommands of this {@link BaseCommand}
     * @return all the command subcommands
     */
    public Set<BaseCommand> getSubcommands() {
        return ImmutableSet.copyOf(subcommands);
    }

    /**
     * Use this method to tell what to do when the command has no more
     * subcommands (run the command) or it has still subcommands (get info
     * when the player failed to run the command).
     *
     * @param sender command executor
     * @param command command executed
     * @param label command label
     * @param args command arguments
     * @return if the command was executed successfully
     */
    public abstract boolean execute(CommandSender sender, Command command, String label, String[] args);
}
