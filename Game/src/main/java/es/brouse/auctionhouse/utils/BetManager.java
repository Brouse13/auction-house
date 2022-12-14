package es.brouse.auctionhouse.utils;

import es.brouse.auctionhouse.AuctionHouse;
import es.brouse.auctionhouse.entities.AuctionHouseItem;
import es.brouse.auctionhouse.serialize.EntitySerializer;
import es.brouse.auctionhouse.translator.Translator;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BetManager {
    public boolean canBet(Player player, AuctionHouseItem item, double addBet) {
        return AuctionHouse.getEconomy().has(player, addBet + item.getPrice());
    }

    public boolean bet(Player player, AuctionHouseItem item, double addBet) {
        //We make a clone of the item to then return money to last better
        AuctionHouseItem clone = item.clone();

        AuctionHouse.getEconomy().createPlayerAccount(player);
        //Just check again if the player can bet for the item
        if (!canBet(player, item, addBet)) return false;

        //We save the balance and then the entity and then return the bet
        if (AuctionHouse.getEconomy().withdrawPlayer(player, addBet + item.getPrice())) {
            return EntitySerializer.getSerializer(updateItem(player, item, addBet)).saveEntity() && returnBet(clone, player);
        }

        return false;
    }


    private boolean returnBet(AuctionHouseItem clone, Player newBetter) {
        //If last_better and owner matches we don't have to return the bet
        System.out.println(clone);
        if (clone.getLast_better().equals(clone.getOwner())) return true;

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(clone.getLast_better()));

        //If we deposited the amount successfully, we send the player a message
        if (AuctionHouse.getEconomy().depositPlayer(offlinePlayer, clone.getPrice())) {
            //TODO send message to offline players
            Player player = Bukkit.getPlayer(offlinePlayer.getUniqueId());
            if (player != null) Logger.player(player, Translator.getString("messages.player.bet.new_bet",
                    newBetter.getName(), clone.getMaterial(), clone.getAmount()));
            return true;
        }
        return false;
    }
    private AuctionHouseItem updateItem(Player player, AuctionHouseItem item, double add) {
        return AuctionHouseItem.builder()
                .identifier(item.getIdentifier())
                .owner(item.getOwner())
                .material(item.getMaterial())
                .last_better(player.getUniqueId().toString())
                .price(item.getPrice() + add)
                .build();
    }
}
