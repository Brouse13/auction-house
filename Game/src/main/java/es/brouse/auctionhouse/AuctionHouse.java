package es.brouse.auctionhouse;

import com.google.common.collect.Sets;
import es.brouse.auctionhouse.commands.AHCommand;
import es.brouse.auctionhouse.commands.BaseCommand;
import es.brouse.auctionhouse.commands.CommandRegister;
import es.brouse.auctionhouse.config.YamlConfig;
import es.brouse.auctionhouse.hooks.Economy;
import es.brouse.auctionhouse.hooks.EconomyProvider;
import es.brouse.auctionhouse.inventory.InventoryController;
import es.brouse.auctionhouse.inventory.SignGUI;
import es.brouse.auctionhouse.nbt.NBTHelper;
import es.brouse.auctionhouse.serialize.EntitySerializer;
import es.brouse.auctionhouse.storage.Mysql;
import es.brouse.auctionhouse.storage.YAML;
import es.brouse.auctionhouse.utils.Logger;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class AuctionHouse extends JavaPlugin {
    @Getter private static SignGUI signGUI;
    @Getter private static YamlConfig settings;
    @Getter private static Economy economy;

    @Override
    public void onEnable() {
        //Init utils
        Logger.init(this);
        YAML.init(this);
        CommandRegister.init(this);
        NBTHelper.init(this);

        //Only enables SignGUI if ProtocolLib
        if (getServer().getPluginManager().isPluginEnabled("ProtocolLib")) {
            SignGUI.init(this);
            signGUI = new SignGUI();
        } else {
            Logger.log("Not found ProtocolLib. Disabling Sign menus");
        }

        //Init static fields
        settings = new YamlConfig();
        economy = new EconomyProvider();

        //Setup mysql if enabled
        if(settings.isMysqlEnabled()) {
            Mysql.init(settings);
            EntitySerializer.setSerializer("mysql");
        }

        //Register all listeners
        listeners().forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));

        //Register commands
        commands().forEach(CommandRegister::register);
    }

    @Override
    public void onDisable() {
    }

    private Set<Listener> listeners() {
        return Sets.newHashSet(new InventoryController());
    }

    private Set<BaseCommand> commands() {
        return Sets.newHashSet(new AHCommand());
    }
}
