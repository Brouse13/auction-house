package es.brouse.auctionhouse.loader.utils;

import es.brouse.auctionhouse.loader.storage.YAML;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Logger {
    private static JavaPlugin plugin;

    /**
     * Singleton that will handle the {@link Logger}
     * @param plugin plugin that will handle the Logger
     */
    public static void init(JavaPlugin plugin) {
        if (Logger.plugin != null)
            throw new IllegalStateException("Logger was already initiated");

        Logger.plugin = plugin;
    }

    /**
     * Log a message to the console with the given
     * log level
     * @param level log level
     * @param msg message
     */
    public static void log(Level level, String msg) {
        plugin.getLogger().log(level, msg);
    }

    /**
     * Log a message to the console with the info level
     * @param message message to log
     */
    public static void log(String message) {
        plugin.getLogger().log(Level.INFO, message);
    }

    /**
     * Log a message to the console with the warn level
     * @param message message to log
     */
    public static void error(String message) {
        plugin.getLogger().log(Level.WARNING, message);
    }

    /**
     * Send a message to the given player
     * @param player player to send the message
     * @param message message to send
     */
    public static void player(Player player, String message) {
        player.sendMessage(message);
    }
}
