package com.qualityplus.assistant.base.event;

import com.qualityplus.assistant.api.event.PlayerHelperEvent;
import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter
public final class PlayerKillEvent extends PlayerHelperEvent {
    private final Entity killed;

    public PlayerKillEvent(final @NotNull Player who, final Entity killed) {
        super(who);
        this.killed = killed;
    }
}
