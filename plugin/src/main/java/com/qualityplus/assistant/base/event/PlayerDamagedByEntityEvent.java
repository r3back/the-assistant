package com.qualityplus.assistant.base.event;

import com.qualityplus.assistant.api.event.PlayerHelperEvent;
import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter
public final class PlayerDamagedByEntityEvent extends PlayerHelperEvent {
    private final Entity entity;

    public PlayerDamagedByEntityEvent(final @NotNull Player who, final Entity entity) {
        super(who);
        this.entity = entity;
    }
}
