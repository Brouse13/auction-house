package es.brouse.auctionhouse;

import es.brouse.auctionhouse.commands.AHCommand;
import es.brouse.auctionhouse.loader.commands.CommandRegister;
import es.brouse.auctionhouse.loader.config.Config;
import es.brouse.auctionhouse.loader.config.MysqlConfig;
import es.brouse.auctionhouse.loader.config.YamlConfig;
import es.brouse.auctionhouse.loader.storage.YAML;
import es.brouse.auctionhouse.loader.utils.Logger;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class AuctionHouse extends JavaPlugin {
    @Getter private Config settings;

    @Override
    public void onEnable() {
        //Init utils
        Logger.init(this);
        YAML.init(this);
        CommandRegister.init(this);

        //SetUp mysql if possible
        settings = new YamlConfig();
        if (settings.isMysqlEnabled()) settings = new MysqlConfig(settings);

        CommandRegister.register(new AHCommand());
    }

    @Override
    public void onDisable() {

    }
}
