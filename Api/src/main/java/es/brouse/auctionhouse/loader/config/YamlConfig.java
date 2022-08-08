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
        return getDefEnum("config.lang", Translator.Lang.class, Translator.Lang.en_US);
    }

    @Override
    public boolean isMysqlEnabled() {
        return getBoolean("config.mysql.enabled", false);
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getDatabase() {
        return null;
    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public ItemStack getNextButton() {
        return ItemBuilder.of(getDefEnum("ahgui.next_button", Material.class, Material.ARROW))
                .displayName(Translator.getString("messages.sectiongui.next_button.name", getLang()))
                .lore(Translator.getStringList("messages.sectiongui.next_button.lore", getLang())).build();
    }

    @Override
    public ItemStack getPreviousButton() {
        return ItemBuilder.of(getDefEnum("ahgui.prev_button", Material.class, Material.ARROW))
                .displayName(Translator.getString("messages.next_button.next_button.name", getLang()))
                .lore(Translator.getStringList("messages.next_button.next_button.lore", getLang())).build();
    }
}
