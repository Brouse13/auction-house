package es.brouse.auctionhouse;

import es.brouse.auctionhouse.commands.AHCommand;
import es.brouse.auctionhouse.loader.commands.CommandRegister;
import es.brouse.auctionhouse.loader.config.Config;
import es.brouse.auctionhouse.loader.config.MysqlConfig;
import es.brouse.auctionhouse.loader.config.YamlConfig;
import es.brouse.auctionhouse.loader.inventory.InventoryController;
import es.brouse.auctionhouse.loader.storage.YAML;
import es.brouse.auctionhouse.loader.utils.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class AuctionHouse extends JavaPlugin {
    private static Config settings;

    @Override
    public void onEnable() {
        //Init utils
        Logger.init(this);
        YAML.init(this);
        CommandRegister.init(this);

        //SetUp mysql if possible
        settings = new YamlConfig();
        if (settings.isMysqlEnabled()) settings = new MysqlConfig(settings);

        this.getServer().getPluginManager().registerEvents(new InventoryController(), this);

        CommandRegister.register(new AHCommand());
    }

    @Override
    public void onDisable() {

    }

    public static Config getSettings() {
        return settings;
    }
}
