package es.brouse.auctionhouse.loader.commands;

import es.brouse.auctionhouse.loader.utils.Logger;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandRegister {
    private static JavaPlugin plugin;

    public static void init(JavaPlugin plugin) {
        if (CommandRegister.plugin != null)
            throw new IllegalStateException("CommandRegister was already initiated");

        CommandRegister.plugin = plugin;
    }

    public static void register(CommandBase command) {
        setExecutor(command.getName(), command);
    }

    public static void unregister(CommandBase command) {
        setExecutor(command.getName(), null);
    }

    private static void setExecutor(String name, CommandBase executor) {
        PluginCommand pluginCommand = plugin.getCommand(name);
        if (pluginCommand == null) {
            Logger.error("Command "+ name+ " is not registered in plugin.yml");
            return;
        }
        pluginCommand.setExecutor(executor);
    }
}
