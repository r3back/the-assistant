package com.qualityplus.assistant.util.message;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * Spigot chat message
 */
@AllArgsConstructor
@NoArgsConstructor
public final class SpecialMessage extends OkaeriConfig {
    public List<String> message;
    public String action;
    public String aboveMessage;

    /**
     * Constructor with message as argument
     *
     * @param message message
     */
    public SpecialMessage(final String message) {
        this.message = Collections.singletonList(message);
    }
}