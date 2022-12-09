package es.brouse.auctionhouse.config;

import es.brouse.auctionhouse.storage.YAML;
import es.brouse.auctionhouse.translator.Translator;
import es.brouse.auctionhouse.utils.builders.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class YamlConfig {
    @Getter private final YAML config = new YAML("config", false);

    //LANG
    public Translator.Lang getLang() {
        return config.getDefEnum("config.lang", Translator.Lang.class, Translator.Lang.en_US);
    }

    //DATABASE
    public boolean isMysqlEnabled() {
        return config.getBoolean("config.mysql.enabled", false);
    }

    public String getJdbc() {
        return config.getString("config.mysql.jdbc");
    }

    public String getUsername() {
        return config.getString("config.mysql.username");
    }

    public String getPassword() {
        return config.getString("config.mysql.password");
    }

    //GUI
    public ItemStack getNextButton() {
        return ItemBuilder.of(config.getDefEnum("ahgui.next_button", Material.class, Material.ARROW))
                .displayName(Translator.getString("messages.sectiongui.next_button.name"))
                .lore(Translator.getStringList("messages.sectiongui.next_button.lore")).build();
    }

    public ItemStack getPreviousButton() {
        return ItemBuilder.of(config.getDefEnum("ahgui.prev_button", Material.class, Material.ARROW))
                .displayName(Translator.getString("messages.sectiongui.prev_button.name"))
                .lore(Translator.getStringList("messages.sectiongui.prev_button.lore")).build();
    }
}
