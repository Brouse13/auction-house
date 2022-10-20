package es.brouse.auctionhouse.flter;

import com.google.common.collect.Lists;
import es.brouse.auctionhouse.entities.AHSection;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class ItemFilter {
    private static final List<String> book_section = Collections.singletonList("BOOK");
    private static final List<String> potion_section = Lists.newArrayList("POTION", "GLASS_BOTTLE",
            "NETHER_WART", "MAGMA_CREAM", "BLAZE_ROD", "BLAZE_POWDER", "GHAST_TEAR", "FERMENTED_SPIDER_EYE",
            "CAULDRON", "GLISTERING_MELON_SLICE", "GOLDEN_CARROT", "RABBIT_FOOT", "DRAGON_BREATH", "PHANTOM_MEMBRANE");

    private static final List<String> armour_section = Lists.newArrayList("SWORD", "SHIELD", "BOW", "ARROW");

    public static AHSection getSection(ItemStack itemStack) {
        if (findAny(book_section, itemStack)) {
            return AHSection.BOOKS;
        } else if (findAny(potion_section, itemStack)) {

        }else if (findAny(armour_section, itemStack)) {
            return AHSection.ARMOUR;
        }

        return null;
    }

    private static boolean findAny(List<String> elements, ItemStack find) {
        for (String element : elements) {
            if (find.getType().toString().contains(element))
                return true;
        }
        return false;
    }
}
