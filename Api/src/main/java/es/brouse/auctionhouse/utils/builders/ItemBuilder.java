package es.brouse.auctionhouse.utils.builders;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {
    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    /**
     * Create a new {@link ItemBuilder} with the given {@link Material}
     * @param material itemStack material
     * @return the instance of the {@link ItemBuilder}
     */
    public static ItemBuilder of(Material material) {
        return new ItemBuilder(new ItemStack(material));
    }

    /**
     * Create a new {@link ItemBuilder} with the given {@link ItemStack}
     * @param itemStack itemStack
     * @return the instance of the {@link ItemBuilder}
     */
    public static ItemBuilder of(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    /**
     * Builder used for the builder pattern
     * @param itemStack starter itemStack
     */
    protected ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        itemMeta = itemStack.getItemMeta();
    }

    /**
     * Set the amount of items
     * @param amount item amount
     * @return the instance of the {@link ItemBuilder}
     */
    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    /**
     * Set a customName to the item
     * @param name name of the item
     * @return the instance of the {@link ItemBuilder}
     */
    public ItemBuilder displayName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    /**
     * Set the lore for the item
     * @param lore lore to add
     * @return the instance of the {@link ItemBuilder}
     */
    public ItemBuilder lore(List<String> lore) {
        itemMeta.setLore(lore);
        return this;
    }

    /**
     * Add the item a new enchantment
     * @param enchantment enchantment to add
     * @param level level of the enchantment
     * @return the instance of the {@link ItemBuilder}
     */
    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    /**
     * Set the item unbreakable
     * @return the instance of the {@link ItemBuilder}
     */
    public ItemBuilder unbreakable() {
        itemMeta.setUnbreakable(true);
        return this;
    }

    /**
     * Build the ItemStack stored on the builder
     * @return built {@link ItemStack}
     */
    public ItemStack build() {
        if (itemStack.getType() != Material.AIR)
            itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
