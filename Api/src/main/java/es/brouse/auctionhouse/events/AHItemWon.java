package es.brouse.auctionhouse.events;

import es.brouse.auctionhouse.entities.AuctionHouseItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Cancellable;

import java.util.UUID;

public class AHItemWon extends AHEvent implements Cancellable {
    @Getter @Setter private boolean cancelled;
    /**
     * Get the item that was bet
     */
    @Getter private final AuctionHouseItem item;

    /**
     * Get the player who has won the bet
     */
    @Getter private final OfflinePlayer winner;

    /**
     * Get the total amount of money spend on the item
     */
    @Getter private final double totalAmount;

    public AHItemWon(AuctionHouseItem item) {
        this.item = item.clone();
        this.winner = Bukkit.getOfflinePlayer(UUID.fromString(item.getLast_better()));
        this.totalAmount = item.getPrice();
    }
}
