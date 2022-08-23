package com.qualityplus.assistant.api.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public abstract class HelperEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    @Setter
    @Getter
    private boolean cancelled = false;

    public HelperEvent() {
        super();
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
