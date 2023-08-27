package com.qualityplus.assistant.base.addons.mythicmobs;

import com.qualityplus.assistant.api.addons.MythicMobsAddon;
import io.lumine.mythic.bukkit.BukkitAPIHelper;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * MythicMobs implementation
 */
public final class MythicMobsAddonImpl implements MythicMobsAddon {
    private final BukkitAPIHelper bukkitAPIHelper = new BukkitAPIHelper();

    @Override
    public boolean isMythicMob(final Entity entity) {
        try {
            final ActiveMob activeMob = this.bukkitAPIHelper.getMythicMobInstance(entity);

            return activeMob != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public String getInternalName(final Entity entity) {
        try {
            final ActiveMob activeMob = this.bukkitAPIHelper.getMythicMobInstance(entity);

            if (activeMob == null) {
                return null;
            }

            return activeMob.getType().getInternalName();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Entity spawn(final String id, final Location location, final int level) {
        /*
        TODO ADD SPAWN LOGIC
         */
        return null;
    }

    @Override
    public String getAddonName() {
        return "MythicMobs";
    }
}
