package es.brouse.auctionhouse.events;

import es.brouse.auctionhouse.entities.AuctionHouseItem;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AHItemBet extends AHEvent {
    /**
     * Get the item that was bet
     */
    @Getter
    private final AuctionHouseItem item;

    /**
     * Get the player who has bet for the new item
     */
    @Getter private final Player better;

    /**
     * Get the previous better for the item
     */
    @Getter private final OfflinePlayer lastBetter;

    /**
     * Get the total amount of money spend on the item
     */
    @Getter private final double amount;

    public AHItemBet(AuctionHouseItem item, Player better, double amount) {
        this.item = item.clone();
        this.better = better;
        this.lastBetter = Bukkit.getOfflinePlayer(UUID.fromString(item.getLast_better()));
        this.amount = amount;
    }
}
