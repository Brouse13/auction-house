package es.brouse.auctionhouse.events;

import es.brouse.auctionhouse.entities.AuctionHouseItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class AHItemCreate extends AHEvent implements Cancellable {
    @Getter @Setter private boolean cancelled;
    /**
     * Get the item that was bet
     */
    @Getter private final AuctionHouseItem item;
    /**
     * Get the player who has bet for the item
     */
    @Getter private final Player owner;

    public AHItemCreate(AuctionHouseItem item) {
        this.item = item.clone();
        this.owner = Bukkit.getPlayer(item.getOwner());
    }
}
