package com.qualityplus.assistant.api.gui;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Lore Wrapper
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class LoreWrapper extends OkaeriConfig {
    private int wrapLength;
    private String wrapStart;
}
