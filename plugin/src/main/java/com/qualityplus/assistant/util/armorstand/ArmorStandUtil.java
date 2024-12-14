package com.qualityplus.assistant.util.armorstand;

import com.qualityplus.assistant.TheAssistantPlugin;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * ArmorStand Utility class
 */
@UtilityClass
public class ArmorStandUtil {
    /**
     * Rotate an armor stand facing a specific location
     *
     * @param armorStand  {@link ArmorStand}
     * @param newLocation {@link Location}
     */
    public void rotate(final ArmorStand armorStand, final Location newLocation) {
        final Location location = armorStand.getLocation().clone();
        final Location finalLocation = location.clone().setDirection(newLocation.clone().subtract(location).toVector());
        Bukkit.getScheduler().runTask(TheAssistantPlugin.getAPI().getPlugin(), () -> armorStand.teleport(finalLocation));
    }

    /**
     * Retrieves if an Armor stand is valid and its alive
     *
     * @param armorStand {@link ArmorStand}
     * @return true if armorstand is valid
     */
    public boolean entityIsValid(final ArmorStand armorStand) {
        return armorStand != null && !armorStand.isDead();
    }

    /**
     * Creates an armor stand in specific location
     *
     * @param location {@link Location}
     * @return {@link ArmorStand}
     */
    public ArmorStand createDefault(final Location location) {
        return Optional.ofNullable(location.getWorld())
                .map(world -> getArmorStand(world, location))
                .orElse(null);
    }

    private ArmorStand getArmorStand(final @NotNull World world, final Location location) {
        final ArmorStand armorStand = world.spawn(location, ArmorStand.class);
        armorStand.setArms(true);
        armorStand.setSmall(true);
        armorStand.setVisible(true);
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
        armorStand.setBasePlate(false);
        armorStand.setCollidable(false);
        armorStand.setCustomNameVisible(false);
        return armorStand;
    }
}
