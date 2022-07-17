package es.brouse.auctionhouse;

import es.brouse.auctionhouse.loader.commands.CommandRegister;
import es.brouse.auctionhouse.loader.utils.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class AuctionHouse extends JavaPlugin {
    @Override
    public void onEnable() {
        Logger.init(this);
        CommandRegister.init(this);
    }

    @Override
    public void onDisable() {

    }
}
