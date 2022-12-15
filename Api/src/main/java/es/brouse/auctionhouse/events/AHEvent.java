package es.brouse.auctionhouse.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AHEvent extends Event {
    /**
     * {@inheritDoc}
     */
    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
