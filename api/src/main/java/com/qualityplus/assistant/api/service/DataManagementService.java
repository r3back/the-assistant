package com.qualityplus.assistant.api.service;

import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * Handles data service layer
 *
 * @param <T> Generic data type
 */
public interface DataManagementService<T> {
    /**
     * Retrieves data for specific player
     *
     * @param player {@link Player}
     * @return Optional of player data
     */
    public default Optional<T> getData(final Player player) {
        return getData(player.getUniqueId());
    }

    /**
     * Retrieves data for specific uuid
     *
     * @param uuid {@link UUID}
     * @return Optional of player data
     */
    public Optional<T> getData(final UUID uuid);

    /**
     * Adds new data
     *
     * @param data to be added
     */
    public void addData(final T data);

    /**
     * Removes data
     *
     * @param data to be removed
     */
    public void removeData(final T data);
}
