package com.qualityplus.assistant.util.message;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * Spigot chat message
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class SpecialMessage extends OkaeriConfig {
    private List<String> message;
    private String action;
    private String aboveMessage;

    /**
     * Constructor with message as argument
     *
     * @param message message
     */
    public SpecialMessage(final String message) {
        this.message = Collections.singletonList(message);
    }
}
