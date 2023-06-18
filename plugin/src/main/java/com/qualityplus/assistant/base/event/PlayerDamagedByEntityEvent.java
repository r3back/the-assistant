package com.qualityplus.assistant.base.event;

import com.qualityplus.assistant.api.event.PlayerAssistantEvent;
import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Event called when a player is attacked by an entity
 */
@Getter
public final class PlayerDamagedByEntityEvent extends PlayerAssistantEvent {
    private final Entity entity;

    /**
     * All args constructor
     *
     * @param who    {@link Player}
     * @param entity {@link Entity}
     */
    public PlayerDamagedByEntityEvent(final @NotNull Player who, final Entity entity) {
        super(who);
        this.entity = entity;
    }
}
