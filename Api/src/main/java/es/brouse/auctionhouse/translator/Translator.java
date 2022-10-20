package es.brouse.auctionhouse.translator;

import com.google.common.collect.Lists;
import es.brouse.auctionhouse.storage.YAML;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Translator {
    private static Function<String, String> colorize = text ->
            ChatColor.translateAlternateColorCodes('&', text);

    /**
     * Get from the messages file the current translation string
     * @param key translation key
     * @param lang translation language
     * @return the translated key
     */
    public static String getString(String key, Lang lang, Object... args) {
        return colorize.apply(ComponentManager.replace(lang.getFile().getString(key, lang+ "."+ key), args));
    }

    /**
     * Get from the messages file the current translation List<String>
     * @param key translation key
     * @param lang translation language
     * @return the translated key
     */
    public static List<String> getStringList(String key, Lang lang, Object... args) {
        List<String> lines = lang.getFile().getStringList(key).stream()
                .map(line -> colorize.apply(ComponentManager.replace(line, args)))
                .collect(Collectors.toList());
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
