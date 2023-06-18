package com.qualityplus.assistant.base.addons.mythicmobs;

import com.qualityplus.assistant.api.addons.MythicMobsAddon;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * Default mythic mobs addon
 */
public final class DefaultMythicMobsAddon implements MythicMobsAddon {
    @Override
    public boolean isMythicMob(final Entity entity) {
        return false;
    }

    @Override
    public String getInternalName(final Entity entity) {
        return null;
    }

    @Override
    public Entity spawn(final String id, final Location location, final int level) {
        return null;
    }

    @Override
    public String getAddonName() {
        return null;
    }
}
