package com.qualityplus.assistant.base.event;

import com.qualityplus.assistant.api.event.PlayerAssistantEvent;
import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Event called when a player kills an entity
 */
@Getter
public final class PlayerKillEvent extends PlayerAssistantEvent {
    private final Entity killed;

    /**
     * All args constructor
     *
     * @param who    {@link Player}
     * @param killed {@link Entity}
     */
    public PlayerKillEvent(final @NotNull Player who, final Entity killed) {
        super(who);
        this.killed = killed;
    }
}
