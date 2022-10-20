package es.brouse.auctionhouse.commands;

import es.brouse.auctionhouse.utils.Logger;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandRegister {
    private static JavaPlugin plugin;

    /**
     * Singleton that will handle the {@link CommandRegister}
     * @param plugin plugin that will handle the CommandRegister
     */
    public static void init(JavaPlugin plugin) {
        if (CommandRegister.plugin != null)
            throw new IllegalStateException("CommandRegister was already initiated");

        CommandRegister.plugin = plugin;
    }

    /**
     * Register a new {@param command} into the plugin
     * @param command command to register
     * @return the registered command
     */
    public static BaseCommand register(BaseCommand command) {
        return pluginRegister(command.getName(), command);
    }

    /**
     * Unregister the {@param command} from the plugin
     * @param command command to unregister
     */
    public static void unregister(BaseCommand command) {
        pluginRegister(command.getName(), null);
    }

    /**
     * Register into the given plugin the command
     * @param name command name
     * @param executor command executor
     * @return the operation status
     */
    private static BaseCommand pluginRegister(String name, BaseCommand executor) {
        PluginCommand pluginCommand = plugin.getCommand(name);
        if (pluginCommand == null) {
            Logger.error("Command "+ name+ " is not registered in plugin.yml");
            return null;
        }

        pluginCommand.setExecutor(executor);
        return executor;
    }
}
