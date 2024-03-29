package com.qualityplus.assistant.api.commands.setup.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Command Setup Event
 *
 * @param <T> Generic command
 */
@Getter
@AllArgsConstructor
public final class CommandSetupEvent<T> {
    private final T command;
}
