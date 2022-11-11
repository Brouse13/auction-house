package es.brouse.auctionhouse.nbt;

import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Function;

@UtilityClass
public class NBTHelper {
    private static JavaPlugin plugin;

    private final Function<String, NamespacedKey> getKey = key ->  new NamespacedKey(plugin, key);

    /**
     * Singleton that will handle the {@link NBTHelper}
     * @param plugin plugin that will handle the NBTHelper
     */
    public static void init(JavaPlugin plugin) {
        //Check the singleton instance for the plugin
        if (NBTHelper.plugin != null)
            throw new IllegalStateException("NBTHelper class has already been instanced");
        NBTHelper.plugin = plugin;
    }


    /**
     * Know if the given itemStack has any NBT stored
     * @param itemStack itemStack to check on
     * @return if the item has NBT
     */
    public static boolean hasNBT(ItemStack itemStack) {
        return getMeta(itemStack).getPersistentDataContainer().getKeys().isEmpty();
    }

    /**
     * Get if the item has the given key on the NBT stored
     * @param itemStack itemStack to check on
     * @param key key to search
     * @return if the key is stored
     */
    public static boolean containsKey(ItemStack itemStack, String key) {
        return getMeta(itemStack).getPersistentDataContainer().getKeys().stream()
                .map(NamespacedKey::getKey).anyMatch(key::equals);
    }

    /**
     * Get the stored key on the itemStack. If not present
     * it will return null
     * @param itemStack itemStack to get from
     * @param key key to search
     * @return the stored key
     */
    public static String getKey(ItemStack itemStack, String key) {
        return getMeta(itemStack).getPersistentDataContainer()
                .get(getKey.apply(key), PersistentDataType.STRING);
    }

    /**
     * Set the given value on the key into the itemStack
     * @param itemStack itemStack to set into
     * @param key key of the value
     * @param value value to set
     */
    public static void setKey(ItemStack itemStack, String key, String value) {
        ItemMeta itemMeta = getMeta(itemStack);

        itemMeta.getPersistentDataContainer().set(getKey.apply(key),
                PersistentDataType.STRING, value);
        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Remove the key from the given itemStack
     * @param itemStack itemStack to set into
     * @param key key to remove
     */
    public static void removeKey(ItemStack itemStack, String key) {
        getMeta(itemStack).getPersistentDataContainer().remove(getKey.apply(key));
    }

    /**
     * Get the {@link PersistentDataContainer} from the itemStack
     * @param itemStack itemStack to get from
     * @return the found persistenceData
     */
    private static ItemMeta getMeta(ItemStack itemStack) {
        return itemStack.getItemMeta();
    }
}
