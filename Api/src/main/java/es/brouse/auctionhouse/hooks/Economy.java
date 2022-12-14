package es.brouse.auctionhouse.hooks;

import org.bukkit.OfflinePlayer;

public interface Economy {
    /**
     * Format the stored amount to the balance format
     * @param amount amount to format
     * @return the balance format
     */
    String format(double amount);

    /**
     * Get the name of the currency in plural
     * @return plural economy name
     */
    String currencyNamePlural();

    /**
     * Get the name of the currency in singular
     * @return singular economy name
     */
    String currencyNameSingular();

    /**
     * Get if the given player has an account created
     * @param player player to check
     * @return if the player has an account
     */
    boolean hasAccount(OfflinePlayer player);

    /**
     * Get the player actual balance
     * @param player player to check
     * @return the player balance
     */
    double getBalance(OfflinePlayer player);

    /**
     * Get if the player has at least {@param amount} in his balance
     * @param player player to check
     * @param amount amount to check
     * @return if the player has enough balance
     */
    boolean has(OfflinePlayer player, double amount);

    /**
     * Subtract from player account the given amount. If the player
     * hasn't got enough balance, or it fails, it will return false.
     * @param player player to withdraw
     * @param amount amount to withdraw
     * @return the operation status
     */
    boolean withdrawPlayer(OfflinePlayer player, double amount);

    /**
     * Deposit into the player account the given amount. If the operation
     * fails, it will return false.
     * @param player player to withdraw
     * @param amount amount to withdraw
     * @return the operation status
     */
    boolean depositPlayer(OfflinePlayer player, double amount);

    /**
     * Create the player account to the given player if the player hasn't
     * got one yet. Otherwise, it will do nothing.
     * @param player player to creat the account
     * @return the operation status
     */
    boolean createPlayerAccount(OfflinePlayer player);
}
