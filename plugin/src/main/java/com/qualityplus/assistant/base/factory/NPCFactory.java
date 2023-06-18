package com.qualityplus.assistant.base.factory;

import com.qualityplus.assistant.api.addons.NPCAddon;
import com.qualityplus.assistant.api.dependency.resolver.DependencyResolver;
import com.qualityplus.assistant.base.addons.npc.CitizensAddon;
import com.qualityplus.assistant.base.addons.npc.DefaultNPCAddon;
import eu.okaeri.injector.OkaeriInjector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Bean;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.plugin.Plugin;

/**
 * NPC addon factory
 */
@Component
public final class NPCFactory {
    private @Inject("injector") OkaeriInjector injector;
    private @Inject DependencyResolver resolver;
    private @Inject Plugin plugin;

    /**
     * Configure npc addon
     *
     * @return {@link NPCAddon}
     */
    @Bean
    public NPCAddon configureNpc() {
        if (this.resolver.isPlugin("Citizens")) {
            return this.injector.createInstance(CitizensAddon.class);
        } else {
            return this.injector.createInstance(DefaultNPCAddon.class);
        }
    }
}
