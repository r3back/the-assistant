package com.qualityplus.assistant.api.addons;

import com.qualityplus.assistant.api.dependency.DependencyPlugin;

import java.util.UUID;

/**
 * MMOItems Addon
 */
public interface MMOItemsAddon extends DependencyPlugin {
    /**
     * Update player stats
     *
     * @param uuid    {@link UUID} player uuid
     * @param ability ability key
     * @param type    ability type
     * @param value   value to add
     */
    public void updateStats(final UUID uuid, final String ability,
                            final String type, final double value);

    /**
     * Retrieves player stats
     *
     * @param uuid    {@link UUID} player uuid
     * @param ability ability key
     * @return ability amount
     */
    public double getStats(final UUID uuid, final String ability);

    /**
     * Retrieves player armor stats
     * @param uuid    {@link UUID} player uuid
     * @param ability ability key
     * @return armor ability amount
     */
    public double getMMOArmor(final UUID uuid, final String ability);
}
