package es.brouse.auctionhouse.loader.config;

import es.brouse.auctionhouse.loader.translator.Translator;
import org.bukkit.inventory.ItemStack;

public class MysqlConfig implements Config {
    public MysqlConfig(Config config) {

    }

    @Override
    public Translator.Lang getLang() {
        return null;
    }

    @Override
    public boolean isMysqlEnabled() {
        return false;
    }

    @Override
    public ItemStack getNextButton() {
        return null;
    }

    @Override
    public ItemStack getPreviousButton() {
        return null;
    }
}
