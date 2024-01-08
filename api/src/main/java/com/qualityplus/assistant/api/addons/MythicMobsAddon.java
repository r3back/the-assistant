package com.qualityplus.assistant.api.addons;

import com.qualityplus.assistant.api.dependency.DependencyPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * Mythic Mobs Addon
 */
public interface MythicMobsAddon extends DependencyPlugin {
    /**
     * Retrieves if an entity is from Mythic Mobs
     *
     * @param entity {@link Entity}
     * @return true if it"s from mythic mobs
     */
    public boolean isMythicMob(final Entity entity);

    /**
     * Retrieves internal mythic mob name
     *
     * @param entity {@link Entity}
     * @return internal mythic mob name
     */
    public String getInternalName(final Entity entity);

    /**
     * Spawns a mythic mob entity
     *
     * @param mythicMobId id from mb
     * @param location    {@link Location} to spawn
     * @param level       mythic mob level
     * @return {@link Entity} Spawned mob
     */
    public Entity spawn(final String mythicMobId, final Location location, final int level);
}
