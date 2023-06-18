package com.qualityplus.assistant.api.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Helper Bukkit event
 */
public abstract class AssistantEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    @Setter
    @Getter
    private boolean cancelled = false;

    /**
     * Default constructor
     */
    public AssistantEvent() {
        super();
    }

    /**
     *
     * @return {@link HandlerList}
     */
    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     *
     * @return {@link HandlerList}
     */
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
