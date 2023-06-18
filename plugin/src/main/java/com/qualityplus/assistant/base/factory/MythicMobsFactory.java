package com.qualityplus.assistant.base.factory;

import com.qualityplus.assistant.api.addons.MythicMobsAddon;
import com.qualityplus.assistant.api.dependency.resolver.DependencyResolver;
import com.qualityplus.assistant.base.addons.mythicmobs.DefaultMythicMobsAddon;
import com.qualityplus.assistant.base.addons.mythicmobs.MythicMobsAddonImpl;
import eu.okaeri.injector.OkaeriInjector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Bean;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.plugin.Plugin;

/**
 * Mythic Mobs addon factory
 */
@Component
public final class MythicMobsFactory {
    private @Inject("injector") OkaeriInjector injector;
    private @Inject DependencyResolver resolver;
    private @Inject Plugin plugin;

    /**
     * Configure mythic mobs addon
     *
     * @return {@link MythicMobsAddon}
     */
    @Bean
    public MythicMobsAddon configureMythicMobs() {
        if (this.resolver.isPlugin("MythicMobs")) {
            return tryToCreateAddon();
        } else {
            return this.injector.createInstance(DefaultMythicMobsAddon.class);
        }
    }

    private MythicMobsAddon tryToCreateAddon() {
        try {
            Class.forName("io.lumine.mythic.bukkit.BukkitAPIHelper");

            return this.injector.createInstance(MythicMobsAddonImpl.class);
        } catch (final Exception e) {
            return this.injector.createInstance(DefaultMythicMobsAddon.class);
        }
    }
}
