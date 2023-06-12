package com.qualityplus.assistant.permissionable;

import org.bukkit.entity.Player;

import java.util.Objects;

public interface Permissionable {
    public String getPermission();

    default boolean hasPermission(final Player player) {
        return getPermission() == null
                || (!Objects.equals(getPermission(), "")
                && player.hasPermission(getPermission()));
    }
}
