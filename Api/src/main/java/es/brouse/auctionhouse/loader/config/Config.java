package es.brouse.auctionhouse.loader.config;

import es.brouse.auctionhouse.loader.translator.Translator;
import org.bukkit.inventory.ItemStack;

public interface Config {
    Translator.Lang getLang();

    boolean isMysqlEnabled();

    ItemStack getNextButton();

    ItemStack getPreviousButton();
}
