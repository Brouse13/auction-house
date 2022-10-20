package es.brouse.auctionhouse.storage;

import es.brouse.auctionhouse.utils.Logger;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class YAML extends YamlConfiguration {
    private static JavaPlugin plugin;
    private File file;

    /**
     * Singleton that will handle the {@link YAML}
     * @param plugin plugin that will handle the YAML
     */
    public static void init(JavaPlugin plugin) {
        //Check the singleton instance for the plugin
        if (YAML.plugin != null)
            throw new IllegalStateException("YAML class has already been instanced");
        YAML.plugin = plugin;
    }

    /**
     * Creates a new empty yml with the specific name on the root directory
     * @param name yaml name
     * @param overwrite if file must be overwritten
     */
    public YAML(String name, boolean overwrite){
        if (plugin == null)
            throw new IllegalStateException("YAML has not been instanced yet");

        //Create new file based on the name
        file = new File(plugin.getDataFolder().getAbsoluteFile()+ "/"+ name+ ".yml");
        file.getParentFile().mkdirs();

        //Create the file based on 'overwrite'
        if (file.exists() && overwrite)
            file.delete();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }

        //Lod the configuration from the file
        try {
            load(file);
        } catch (InvalidConfigurationException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Apply the changes on the {@link YAML} into the file
     */
    public void save() {
        try {
            save(file);
        }catch (IOException exception) {
            Logger.error("Unable to save file " + file.getName());
        }
    }

    /**
     * Reload the content of the {@link YAML}
     */
    public void reload() {
        save();
        setDefaults(YamlConfiguration.loadConfiguration(file));

    }

    /**
     * Get from the config an enum that matches with {@param type}. If the enum does not
     * contain the value retrieved on the config it will return {@param defValue}
     * @param key key to lookup
     * @param type type of the enum
     * @param defValue dfault value
     * @return the found value on the enum, otherwise the {@param defValue}
     * @param <T> Enum type
     */
    public <T extends Enum<T>> T getDefEnum(String key, Class<T> type, T defValue) {
        try {
            return Enum.valueOf(type, getString(key, key));
        }catch (Exception exception) {
            return defValue;
        }
    }
}
