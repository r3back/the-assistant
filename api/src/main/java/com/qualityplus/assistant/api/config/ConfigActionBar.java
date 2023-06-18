package com.qualityplus.assistant.api.config;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Config for Action Bar
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class ConfigActionBar extends OkaeriConfig {
    private String message;
    private boolean enabled;
    private List<String> disabledWorlds;
    private List<String> disabledRegions;
}
