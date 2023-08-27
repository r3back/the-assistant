package com.qualityplus.assistant.api.commands.setup;

import com.qualityplus.assistant.api.commands.setup.event.CommandSetupEvent;

/**
 * Command handler
 *
 * @param <T> command event generic type
 */
@FunctionalInterface
public interface CommandHandler<T> {
    /**
     * Command complete event callback
     *
     * @param event {@link CommandSetupEvent}
     */
    public void onCompleteCommand(final CommandSetupEvent<T> event);
}
