package com.qualityplus.assistant.api.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Player Helper Bukkit event
 */
public abstract class PlayerAssistantEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    @Setter
    @Getter
    private boolean cancelled = false;

    /**
     * Default player constructor
     *
     * @param who {@link Player}
     */
    public PlayerAssistantEvent(final @NotNull Player who) {
        super(who);
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
