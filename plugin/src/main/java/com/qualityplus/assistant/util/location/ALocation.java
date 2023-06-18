package com.qualityplus.assistant.util.location;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Serializable World Location
 */
@Getter
@Setter
@AllArgsConstructor
public final class ALocation extends OkaeriConfig {
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;
    private String world;

    /**
     * Constructor with location as argument
     *
     * @param location {@link Location}
     */
    public ALocation(final Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.world = location.getWorld().getName();
    }

    /**
     * Retrieves bukkit location
     *
     * @return {@link Location}
     */
    public Location getLocation() {
        return new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z, this.yaw, this.pitch);
    }

    /**
     * Check if two locations are equals
     *
     * @param location {@link Location}
     * @return true if are equals
     */
    public boolean equals(final ALocation location) {
        return location.getWorld().equals(this.world) &&
                location.getX() == this.x &&
                location.getY() == this.y &&
                location.getZ() == this.z &&
                location.getYaw() == this.yaw &&
                location.getPitch() == this.pitch;
    }
}
