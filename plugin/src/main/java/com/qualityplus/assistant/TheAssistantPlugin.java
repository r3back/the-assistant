package com.qualityplus.assistant;

import com.qualityplus.assistant.api.TheAssistantAPI;
import com.qualityplus.assistant.api.config.ConfigSlimeWorldManager;
import com.qualityplus.assistant.base.config.Config;
import com.qualityplus.assistant.okaeri.OkaeriSilentPlugin;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Bean;
import eu.okaeri.platform.core.annotation.Scan;

/**
 * The Assistant Plugin Main class
 */
@Scan(deep = true, exclusions = {
        "com.qualityplus.assistant.lib",
        "com.qualityplus.assistant.base.addons.placeholders",
        "com.qualityplus.assistant.inventory",
        "com.qualityplus.assistant.base.nms"
})
public final class TheAssistantPlugin extends OkaeriSilentPlugin {
    private static @Inject TheAssistantAPI api;

    /**
     * Retrieves The assistant api
     *
     * @return {@link TheAssistantAPI}
     */
    public static TheAssistantAPI getAPI() {
        return api;
    }

    /**
     * Creates Bean for Slime World Manager config
     *
     * @param config {@link Config} config file
     * @return {@link ConfigSlimeWorldManager}
     */
    @Bean
    public ConfigSlimeWorldManager setupSlimeWorld(final @Inject Config config) {
        return config.getConfigSlimeWorldManager();
    }
}
