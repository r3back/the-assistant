package com.qualityplus.assistant.base.event;

import com.qualityplus.assistant.api.event.PlayerAssistantEvent;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter
public final class ActionBarMessageEvent extends PlayerAssistantEvent {
    private final ActionBarType type;
    private final String message;

    public ActionBarMessageEvent(final @NotNull Player who, final String message,
                                 final ActionBarType type) {
        super(who);

        this.message = message;
        this.type = type;
    }

    public enum ActionBarType {
        GAME_INFO,
        ACTION_BAR_TEXT;
    }
}
