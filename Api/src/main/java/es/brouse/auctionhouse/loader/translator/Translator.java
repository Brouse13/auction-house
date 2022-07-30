package es.brouse.auctionhouse.loader.translator;

import es.brouse.auctionhouse.loader.storage.YAML;
import lombok.Getter;

import java.util.List;

public class Translator extends YAML {

    public Translator() {
        super("messages", false);
    }

    /**
     * Get from the messages file the current translation string
     * @param key translation key
     * @param lang translation language
     * @return the translated key
     */
    public static String getString(String key, Lang lang) {
        return lang.getFile().getString(key, lang+ "."+ key);
    }

    /**
     * Get from the messages file the current translation List<String>
     * @param key translation key
     * @param lang translation language
     * @return the translated key
     */
    public static List<String> getStringList(String key, Lang lang) {
        return lang.getFile().getStringList(key);
    }

    public enum Lang {
        en_US(new YAML("messages/nes_US", false)),
        es_ES(new YAML("messages/es_ES", false)),
        ca_ES(new YAML("messages/ca/ES", false));
        @Getter private final YAML file;

        Lang(YAML file) {
            this.file = file;
        }
    }
}
