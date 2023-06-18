package com.qualityplus.assistant.base.event;

import com.qualityplus.assistant.api.event.PlayerAssistantEvent;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Event when Action Bar is sent
 */
@Getter
public final class ActionBarMessageEvent extends PlayerAssistantEvent {
    private final ActionBarType type;
    private final String message;

    /**
     * All Args Constructor
     *
     * @param who     {@link Player}
     * @param message action bar message
     * @param type    {@link ActionBarType}
     */
    public ActionBarMessageEvent(final @NotNull Player who, final String message,
                                 final ActionBarType type) {
        super(who);

        this.message = message;
        this.type = type;
    }

    /**
     * Represent ActionBar types
     */
    public enum ActionBarType {
        /**
         * Game Info
         */
        GAME_INFO,
        /**
         * Action Bar Text
         */
        ACTION_BAR_TEXT
    }
}
