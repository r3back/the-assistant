package com.qualityplus.assistant.permissionable;

import org.bukkit.entity.Player;

import java.util.Objects;

/**
 * Permissionable Item
 */
public interface Permissionable {
    /**
     * Retrieves permission for this object
     *
     * @return permission
     */
    public String getPermission();

    /**
     * Retrieves if a player has the required permission
     *
     * @param player {@link Player}
     * @return true if player has permission
     */
    public default boolean hasPermission(final Player player) {
        return getPermission() == null
                || (!Objects.equals(getPermission(), "")
                && player.hasPermission(getPermission()));
    }
}
