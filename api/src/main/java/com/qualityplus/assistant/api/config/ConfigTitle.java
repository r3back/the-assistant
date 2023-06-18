package com.qualityplus.assistant.api.config;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Config for Titles
 */
@Getter
@AllArgsConstructor
public final class ConfigTitle extends OkaeriConfig {
    private String title;
    private String subtitle;
}
