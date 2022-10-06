package es.brouse.auctionhouse.loader.nbt;

import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

@UtilityClass
public class NBTHelper {

    /**
     * Know if the given itemStack has any NBT stored
     * @param itemStack itemStack to check on
     * @return if the item has NBT
     */
    public static boolean hasNBT(ItemStack itemStack) {
        return getContainer(itemStack).getKeys().isEmpty();
    }

    /**
     * Get if the item has the given key on the NBT stored
     * @param itemStack itemStack to check on
     * @param key key to search
     * @return if the key is stored
     */
    public static boolean containsKey(ItemStack itemStack, String key) {
        return getContainer(itemStack).getKeys().stream().map(NamespacedKey::getKey).anyMatch(key::equals);
    }

    /**
     * Get the stored key on the itemStack. If not present
     * it will return null
     * @param itemStack itemStack to get from
     * @param key key to search
     * @return the stored key
     */
    public static String getKey(ItemStack itemStack, String key) {
        return getContainer(itemStack).get(new NamespacedKey("ah", key), PersistentDataType.STRING);
    }

    /**
     * Set the given value on the key into the itemStack
     * @param itemStack itemStack to set into
     * @param key key of the value
     * @param value value to set
     */
    public static void setKey(ItemStack itemStack, String key, String value) {
        getContainer(itemStack).set(new NamespacedKey("ah", key), PersistentDataType.STRING, value);
    }

    /**
     * Remove the key from the given itemStack
     * @param itemStack itemStack to set into
     * @param key key to remove
     */
    public static void removeKey(ItemStack itemStack, String key) {
        getContainer(itemStack).remove(new NamespacedKey("ah", key));
    }

    /**
     * Get the {@link PersistentDataContainer} from the itemStack
     * @param itemStack itemStack to get from
     * @return the found persistenceData
     */
    private static PersistentDataContainer getContainer(ItemStack itemStack) {
        return itemStack.getItemMeta().getPersistentDataContainer();
    }
}
