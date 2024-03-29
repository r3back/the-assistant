package com.qualityplus.assistant.api.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Event called when Sign is closed
 */
@Getter
@AllArgsConstructor
public final class SignCompletedEvent {
    private final Player player;
    private final List<String> lines;
}
