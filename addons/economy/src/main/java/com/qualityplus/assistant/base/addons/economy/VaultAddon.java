package com.qualityplus.assistant.base.addons.economy;

import com.qualityplus.assistant.api.addons.EconomyAddon;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Optional;

/**
 * Vault Implementation
 */
public final class VaultAddon implements EconomyAddon {
    private final Economy economy = setupEconomy();

    @Override
    public double getMoney(final OfflinePlayer player) {
        return Optional.ofNullable(economy).map(eco -> eco.getBalance(player)).orElse(0d);
    }

    @Override
    public void withdrawMoney(final OfflinePlayer player, final double amount) {
        Optional.ofNullable(economy).ifPresent(eco -> eco.withdrawPlayer(player, amount));
    }

    @Override
    public void depositMoney(final OfflinePlayer player, final double amount) {
        Optional.ofNullable(economy).ifPresent(eco -> eco.depositPlayer(player, amount));
    }

    private Economy setupEconomy() {
        final RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

        return rsp == null ? null : rsp.getProvider();
    }

    @Override
    public String getAddonName() {
        return "Vault";
    }
}
