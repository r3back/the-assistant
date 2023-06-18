package com.qualityplus.assistant.api.addons;

import com.qualityplus.assistant.api.dependency.DependencyPlugin;
import org.bukkit.OfflinePlayer;

/**
 * Economy Addon
 */
public interface EconomyAddon extends DependencyPlugin {
    /**
     * Retrieves player money
     *
     * @param player {@link OfflinePlayer}
     * @return money amount
     */
    public double getMoney(final OfflinePlayer player);

    /**
     * Deposit money to player balance
     *
     * @param player {@link OfflinePlayer}
     * @param amount money to add
     */
    public void depositMoney(final OfflinePlayer player, final double amount);

    /**
     * Withdraw money from player balance
     *
     * @param player {@link OfflinePlayer}
     * @param amount money to remove
     */
    public void withdrawMoney(final OfflinePlayer player, final double amount);
}
