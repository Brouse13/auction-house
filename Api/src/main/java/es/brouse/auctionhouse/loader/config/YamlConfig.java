package es.brouse.auctionhouse.loader.config;

import es.brouse.auctionhouse.loader.storage.YAML;
import es.brouse.auctionhouse.loader.translator.Translator;
import es.brouse.auctionhouse.loader.utils.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class YamlConfig extends YAML implements Config {
    /**
     * Creates the default config file
     */
    public YamlConfig() {
        super("config", false);
    }

    @Override
    public Translator.Lang getLang() {
        try {
            return Translator.Lang.valueOf(getString("config.lang", "en_US"));
        }catch (IllegalArgumentException exception) {
            return Translator.Lang.en_US;
        }
    }

    @Override
    public boolean isMysqlEnabled() {
        return getBoolean("config.mysql.enabled", false);
    }

    @Override
    public ItemStack getNextButton() {
        return ItemBuilder.of(getDefEnum("gui.next_button.item", Material.class, Material.ARROW))
                .displayName(Translator.getString("messages.next_button.name", getLang()))
                .lore(Translator.getStringList("messages.nex_button.lore", getLang())).build();
    }

    @Override
    public ItemStack getPreviousButton() {
        return ItemBuilder.of(getDefEnum("gui.prev_bu.item", Material.class, Material.ARROW))
                .displayName(Translator.getString("messages.next_button.name", getLang()))
                .lore(Translator.getStringList("messages.nex_button.lore", getLang())).build();
    }
}
