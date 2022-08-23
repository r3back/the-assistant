package com.qualityplus.assistant.config;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ConfigTitle extends OkaeriConfig {
    private String title;
    private String subtitle;
}
