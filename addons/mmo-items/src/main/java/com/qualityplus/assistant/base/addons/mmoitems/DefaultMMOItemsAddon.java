package com.qualityplus.assistant.base.addons.mmoitems;

import com.qualityplus.assistant.api.addons.MMOItemsAddon;

import java.util.UUID;

/**
 * Default MMOItems Addon Implementation
 */
public final class DefaultMMOItemsAddon implements MMOItemsAddon {
    @Override
    public String getAddonName() {
        return null;
    }

    @Override
    public void updateStats(final UUID uuid, final String ability, final String type, final double value) {

    }

    @Override
    public double getStats(final UUID uuid, final String ability) {
        return 0;
    }

    @Override
    public double getMMOArmor(final UUID uuid, final String ability) {
        return 0;
    }
}
