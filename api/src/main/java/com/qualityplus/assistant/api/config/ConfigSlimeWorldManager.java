package com.qualityplus.assistant.api.config;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Config for SlimeWorldManager
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class ConfigSlimeWorldManager extends OkaeriConfig {
    private String slimeWorldManagerSource = "file";
}
