package es.brouse.auctionhouse.listeners;

import es.brouse.auctionhouse.AuctionHouse;
import es.brouse.auctionhouse.entities.AuctionHouseItem;
import es.brouse.auctionhouse.events.AHItemBet;
import es.brouse.auctionhouse.events.AHItemCreate;
import es.brouse.auctionhouse.events.AHItemWon;
import es.brouse.auctionhouse.translator.Translator;
import es.brouse.auctionhouse.utils.BetManager;
import es.brouse.auctionhouse.utils.Logger;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class AHListeners implements Listener {
    private final BetManager betManager = new BetManager();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onAHItemBet(AHItemBet event) {
        Player player = event.getBetter();
        AuctionHouseItem item = event.getItem();
        OfflinePlayer lastBetter = event.getLastBetter();

        if (lastBetter.getUniqueId().equals(player.getUniqueId())) {
            Logger.player(player, Translator.getString("messages.player.bet.already_bet"));
            return;
        }

        if (item.getOwner().equals(player.getUniqueId().toString())) {
            Logger.player(player, Translator.getString("messages.player.bet.your_item"));
            return;
        }

        if (!AuctionHouse.getEconomy().has(player, item.getAmount())) {
            Logger.player(player, Translator.getString("messages.player.bet.not_money"));
            return;
        }

        if (betManager.bet(player, item, event.getAmount())) {
            Logger.player(player, Translator.getString("messages.player.bet.success",
                    (item.getPrice() + event.getAmount()), item.getMaterial()));
        }else {
            Logger.player(player, Translator.getString("messages.player.bet.error"));
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onAHItemCreate(AHItemCreate event) {
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onAHItemWon(AHItemWon event) {
    }
}
