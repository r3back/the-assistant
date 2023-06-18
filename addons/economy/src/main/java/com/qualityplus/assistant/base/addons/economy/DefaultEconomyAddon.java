package com.qualityplus.assistant.base.addons.economy;

import com.qualityplus.assistant.api.addons.EconomyAddon;
import org.bukkit.OfflinePlayer;

/**
 * Default Economy Addon Implementation
 */
public final class DefaultEconomyAddon implements EconomyAddon {
    @Override
    public double getMoney(final OfflinePlayer player) {
        return 0;
    }

    @Override
    public void depositMoney(final OfflinePlayer player, final double amount) {

    }

    @Override
    public void withdrawMoney(final OfflinePlayer player, final double amount) {

    }

    @Override
    public String getAddonName() {
        return null;
    }
}
