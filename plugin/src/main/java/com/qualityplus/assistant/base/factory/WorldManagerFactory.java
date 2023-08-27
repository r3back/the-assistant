package com.qualityplus.assistant.base.factory;

import com.qualityplus.assistant.api.addons.WorldManagerAddon;
import com.qualityplus.assistant.api.config.ConfigSlimeWorldManager;
import com.qualityplus.assistant.api.dependency.resolver.DependencyResolver;
import com.qualityplus.assistant.base.addons.worldmanager.DefaultWorldManagerAddon;
import com.qualityplus.assistant.base.addons.worldmanager.SlimeWorldAddon;
import eu.okaeri.injector.OkaeriInjector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Bean;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.plugin.Plugin;

/**
 * World Manager addon factory
 */
@Component
public final class WorldManagerFactory {
    private @Inject("injector") OkaeriInjector injector;
    private @Inject ConfigSlimeWorldManager configSlime;
    private @Inject DependencyResolver resolver;
    private @Inject Plugin plugin;

    /**
     * Configure World manager addon
     *
     * @return {@link WorldManagerAddon}
     */
    @Bean
    public WorldManagerAddon configureWorldManager() {
        if (this.resolver.isPlugin("SlimeWorldManager") && classExists()) {
            final WorldManagerAddon addon = this.injector.createInstance(SlimeWorldAddon.class);
            addon.setup();
            return addon;
        } else {
            return this.injector.createInstance(DefaultWorldManagerAddon.class);
        }
    }

    private boolean classExists() {
        try {
            Class.forName("com.grinderwolf.swm.api.world.properties.SlimePropertyMap");
            Class.forName("com.grinderwolf.swm.api.world.SlimeWorld");
            Class.forName("com.grinderwolf.swm.api.loaders.SlimeLoader");

            return true;
        } catch (final ClassNotFoundException e) {
            return false;
        }
    }
}
