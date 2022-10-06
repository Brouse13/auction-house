package hooks;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Server;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultEconomy {
    @Getter private static Economy economy;

    public static boolean RegisterEconomy(Server server) {
        if (server.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> serviceProvider = server.getServicesManager().getRegistration(Economy.class);
        if (serviceProvider == null) {
            return false;
        }

        economy = serviceProvider.getProvider();
        return economy != null;
    }
}
