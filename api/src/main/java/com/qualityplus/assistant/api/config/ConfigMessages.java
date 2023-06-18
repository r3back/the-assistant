package com.qualityplus.assistant.api.config;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Config for multiple Messages
 */
@Getter
@Setter
@AllArgsConstructor
public final class ConfigMessages extends OkaeriConfig {
    private List<String> messages;
    private boolean enabled;
}
