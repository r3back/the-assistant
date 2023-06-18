package com.qualityplus.assistant.base.config;

import com.qualityplus.assistant.api.config.ConfigSlimeWorldManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import eu.okaeri.platform.core.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

/**
 * Config file
 */
@Getter
@Setter
@Configuration()
@Header("================================")
@Header("       Config      ")
@Header("================================")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public final class Config extends OkaeriConfig {
    private String prefix = "[TheAssistant] ";
    private ConfigSlimeWorldManager configSlimeWorldManager = new ConfigSlimeWorldManager();
}
