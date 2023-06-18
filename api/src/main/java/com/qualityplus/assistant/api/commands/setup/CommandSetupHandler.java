package com.qualityplus.assistant.api.commands.setup;

import com.qualityplus.assistant.api.commands.setup.event.CommandSetupEvent;

/**
 * Command setup handler
 *
 * @param <T> command setup event generic type
 */
@FunctionalInterface
public interface CommandSetupHandler<T> {
    /**
     * Command complete event callback
     *
     * @param event {@link CommandSetupEvent}
     */
    public void onCompleteCommand(final CommandSetupEvent<T> event);
}