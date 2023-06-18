package com.qualityplus.assistant.api.config;

import eu.okaeri.configs.OkaeriConfig;
import lombok.*;

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
