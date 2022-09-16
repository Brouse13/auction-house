package es.brouse.auctionhouse.loader.translator;
import com.google.common.collect.Lists;
import es.brouse.auctionhouse.loader.storage.YAML;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Translator {
    /**
     * Get from the messages file the current translation string
     * @param key translation key
     * @param lang translation language
     * @return the translated key
     */
    public static String getString(String key, Lang lang) {
        return ChatColor.translateAlternateColorCodes('&',
                lang.getFile().getString(key, lang+ "."+ key));
    }

    /**
     * Get from the messages file the current translation List<String>
     * @param key translation key
     * @param lang translation language
     * @return the translated key
     */
    public static List<String> getStringList(String key, Lang lang) {
        List<String> lines = lang.getFile().getStringList(key).stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());
        return lines.isEmpty() ? Lists.newArrayList("[" + lang+ "." + key + "]") : lines;
    }

    public enum Lang {
        en_US(new YAML("messages/es_US", false)),
        es_ES(new YAML("messages/es_ES", false)),
        ca_ES(new YAML("messages/ca_ES", false));
        @Getter private final YAML file;

        Lang(YAML file) {
            this.file = file;
        }
    }
}
