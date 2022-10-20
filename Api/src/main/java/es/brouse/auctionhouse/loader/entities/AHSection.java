package es.brouse.auctionhouse.loader.entities;

import lombok.Getter;
import org.bukkit.Material;

public enum AHSection {
    FOOD(Material.APPLE),
    ARMOUR(Material.DIAMOND_CHESTPLATE),
    WEAPONS(Material.DIAMOND_SWORD),
    BLOCKS(Material.BRICKS),
    REDSTONE(Material.REDSTONE),
    BOOKS(Material.ENCHANTED_BOOK),
    POTIONS(Material.GLASS_BOTTLE);

    @Getter private final Material defMaterial;

    AHSection(Material defMaterial) {
        this.defMaterial = defMaterial;
    }
}
