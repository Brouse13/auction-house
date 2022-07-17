package es.brouse.auctionhouse.loader.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class YAML extends YamlConfiguration {
    private static JavaPlugin plugin;

    /**
     * Creates a new empty yml with the specific name on the root directory
     * @param name yaml name
     * @param overwrite if file must be overwritten
     * @throws IOException if there was any exception while creating the file
     */
    public YAML(String name, boolean overwrite){
        if (plugin == null)
            throw new IllegalStateException("YAML has not been instanced yet");

        //Create new file based on the name
        File file = new File(plugin.getDataFolder().getAbsoluteFile()+ "/"+ name+ ".yml");
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

    public static void init(JavaPlugin plugin) {
        //Check the singleton instance for the plugin
        if (YAML.plugin != null)
            throw new IllegalStateException("YAML class has already been instanced");
        YAML.plugin = plugin;
    }
}
