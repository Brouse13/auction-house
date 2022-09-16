package es.brouse.auctionhouse;

import com.google.common.collect.Sets;
import es.brouse.auctionhouse.commands.AHCommand;
import es.brouse.auctionhouse.loader.commands.BaseCommand;
import es.brouse.auctionhouse.loader.commands.CommandRegister;
import es.brouse.auctionhouse.loader.config.YamlConfig;
import es.brouse.auctionhouse.loader.inventory.InventoryController;
import es.brouse.auctionhouse.loader.serializer.AuctionHouseItem;
import es.brouse.auctionhouse.loader.serializer.SerializerManager;
import es.brouse.auctionhouse.loader.serializer.serializers.MysqlSerializer;
import es.brouse.auctionhouse.loader.serializer.serializers.Serializer;
import es.brouse.auctionhouse.loader.serializer.serializers.YAMLSerializer;
import es.brouse.auctionhouse.loader.storage.Mysql;
import es.brouse.auctionhouse.loader.storage.YAML;
import es.brouse.auctionhouse.loader.utils.Logger;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
import java.util.UUID;

public class AuctionHouse extends JavaPlugin {
    @Getter private static YamlConfig settings;

    @Override
    public void onEnable() {
        //Init utils
        Logger.init(this);
        YAML.init(this);
        CommandRegister.init(this);

        //Setup config
        settings = new YamlConfig();

        //Setup mysql if enabled
        if(settings.isMysqlEnabled())
            Mysql.init(settings);

        //Register all the serializers
        SerializerManager.addSerializer("yaml", YAMLSerializer.class);
        SerializerManager.addSerializer("mysql", MysqlSerializer.class);

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
