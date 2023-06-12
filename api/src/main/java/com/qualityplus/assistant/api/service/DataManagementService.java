package com.qualityplus.assistant.api.service;

import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public interface DataManagementService<T> {
    public default Optional<T> getData(Player player) {
        return getData(player.getUniqueId());
    }

    public Optional<T> getData(UUID uuid);

    public void addData(T data);

    public void removeData(T data);
}
