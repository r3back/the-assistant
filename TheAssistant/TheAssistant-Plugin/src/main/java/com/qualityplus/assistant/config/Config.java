package com.qualityplus.assistant.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import eu.okaeri.platform.core.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration()
@Header("================================")
@Header("       Configuration      ")
@Header("================================")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public final class Config extends OkaeriConfig {
    @Comment("Plugin Prefix")
    @CustomKey("prefix")
    public String prefix = "[TheHelper] ";
}