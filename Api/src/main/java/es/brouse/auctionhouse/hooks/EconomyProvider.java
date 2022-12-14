package es.brouse.auctionhouse.hooks;

import es.brouse.auctionhouse.entities.Balance;
import es.brouse.auctionhouse.serialize.EntitySerializer;
import es.brouse.auctionhouse.serialize.serializers.Serializer;
import es.brouse.auctionhouse.translator.Translator;
import org.bukkit.OfflinePlayer;

public class EconomyProvider implements Economy {

    @Override
    public String format(double amount) {
        return String.format("%1$.2f", amount);
    }

    @Override
    public String currencyNamePlural() {
        return Translator.getString("messages.economy.singularName");
    }

    @Override
    public String currencyNameSingular() {
        return Translator.getString("messages.economy.pluralName");
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        return getSerializer(player).getEntity() != null;
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        if (!hasAccount(player)) return -1;
        return getSerializer(player).getEntity().getBalance();
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        return getBalance(player) >= amount;
    }

    @Override
    public boolean withdrawPlayer(OfflinePlayer player, double amount) {
        if (!has(player, amount)) return false;

        Balance balance = Balance.builder()
                .identifier(player.getUniqueId().toString())
                .balance(getBalance(player) - amount).build();
        return EntitySerializer.getSerializer(balance).saveEntity();
    }

    @Override
    public boolean depositPlayer(OfflinePlayer player, double amount) {
        if (!has(player, amount)) return false;

        Balance balance = Balance.builder()
                .identifier(player.getUniqueId().toString())
                .balance(getBalance(player) + amount).build();
        return EntitySerializer.getSerializer(balance).saveEntity();
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        if (hasAccount(player)) return false;

        Balance balance = Balance.builder().identifier(player.getUniqueId().toString()).balance(0).build();
        return EntitySerializer.getSerializer(balance).saveEntity();
    }

    private Serializer<Balance> getSerializer(OfflinePlayer player) {
        Balance balance = Balance.builder().identifier(player.getUniqueId().toString()).build();
        return EntitySerializer.getSerializer(balance);
    }
}
