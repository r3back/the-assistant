package com.qualityplus.assistant.api.sign.handler;

import com.qualityplus.assistant.api.event.SignCompletedEvent;

/**
 * Handle when a Sign Gui is closed
 */
@FunctionalInterface
public interface SignCompleteHandler {
    /**
     * Handles action when sign is closed
     *
     * @param event {@link SignCompletedEvent}
     */
    public void onSignClose(final SignCompletedEvent event);
}
