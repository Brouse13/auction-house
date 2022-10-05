package es.brouse.auctionhouse.loader.translator;

import com.google.common.collect.Sets;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComponentManager {
    private static final Pattern pattern = Pattern.compile("\\{(.*?)}");

    public static String replace(String message, Object... args) {
        Set<String> replacers = getReplacers(message);

        if (replacers.isEmpty()) return message;

        for (String replacer : replacers) {
            try {
                int index = Integer.parseInt(replacer
                        .replaceAll("\\{", "")
                        .replaceAll("\\}", ""));
                message = message.replace(replacer, String.valueOf(args[index]));
            }catch (Exception exception) {
                //Ignore that replacing
            }
        }
        return message;
    }

    private static Set<String> getReplacers(String message) {
        Set<String> matches = Sets.newHashSet();

        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            matches.add(matcher.group());
        }

        return matches;
    }
}
