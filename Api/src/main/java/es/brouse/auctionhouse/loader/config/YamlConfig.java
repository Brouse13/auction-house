package es.brouse.auctionhouse.loader.config;

import es.brouse.auctionhouse.loader.storage.YAML;
import es.brouse.auctionhouse.loader.translator.Translator;
import es.brouse.auctionhouse.loader.utils.builders.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class YamlConfig {
    //LANG
    @Getter private final Translator.Lang lang;

    //DATABASE
    @Getter private final boolean isMysqlEnabled;
    @Getter private final String jdbc;
    @Getter private final String username;
    @Getter private final String password;

    //GUI
    @Getter private final ItemStack nextButton;
    @Getter private final ItemStack previousButton;

    /**
     * Creates the default config file
     */
    public YamlConfig() {
        YAML config = new YAML("config", false);

        lang = config.getDefEnum("config.lang", Translator.Lang.class, Translator.Lang.en_US);

        isMysqlEnabled = config.getBoolean("config.mysql.enabled", false);
        jdbc = config.getString("config.mysql.jdbc");
        username = config.getString("config.mysql.username");
        password = config.getString("config.mysql.password");

        nextButton = ItemBuilder.of(config.getDefEnum("ahgui.next_button", Material.class, Material.ARROW))
                .displayName(Translator.getString("messages.sectiongui.next_button.name", getLang()))
                .lore(Translator.getStringList("messages.sectiongui.next_button.lore", getLang())).build();
        previousButton = ItemBuilder.of(config.getDefEnum("ahgui.prev_button", Material.class, Material.ARROW))
                .displayName(Translator.getString("messages.next_button.next_button.name", getLang()))
                .lore(Translator.getStringList("messages.next_button.next_button.lore", getLang())).build();

    }
}
