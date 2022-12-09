package es.brouse.auctionhouse.translator;

import com.google.common.collect.Lists;
import es.brouse.auctionhouse.config.YamlConfig;
import es.brouse.auctionhouse.storage.YAML;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Translator {
    private static Lang language = new YamlConfig().getLang();
    private static final Function<String, String> colorize = text ->
            ChatColor.translateAlternateColorCodes('&', text);

    /**
     * Get from the messages file the current translation {@link String} using
     * the load language. It is generated using {@code new YamlConfig().getLang()}.
     * To reload the language use {@link Translator#reload()}.
     * @param key translation key
     * @return the translated key
     */
    public static String getString(String key, Object... args) {
        return colorize.apply(ComponentManager.replace(language.getFile()
                .getString(key, language + "."+ key), args));
    }

    /**
     * Get from the messages file the current translation {@link List<String>} using
     * the load language. It is generated using {@code new YamlConfig().getLang()}.
     * To reload the language use {@link Translator#reload()}.
     * @param key translation key
     * @return the translated key
     */
    public static List<String> getStringList(String key, Object... args) {
        List<String> lines = language.getFile().getStringList(key).stream()
                .map(line -> colorize.apply(ComponentManager.replace(line, args)))
                .collect(Collectors.toList());
        return lines.isEmpty() ? Lists.newArrayList("[" + language+ "." + key + "]") : lines;
    }

    public static Lang reload() {
        language = new YamlConfig().getLang();
        return language;
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
