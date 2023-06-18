package com.qualityplus.assistant.base.factory;

import com.qualityplus.assistant.api.addons.MMOItemsAddon;
import com.qualityplus.assistant.api.dependency.resolver.DependencyResolver;
import com.qualityplus.assistant.base.addons.mmoitems.DefaultMMOItemsAddon;
import com.qualityplus.assistant.base.addons.mmoitems.MMOItemsAddonImpl;
import eu.okaeri.injector.OkaeriInjector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Bean;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.plugin.Plugin;

/**
 * MMOItems addon factory
 */
@Component
public final class MMOItemsFactory {
    private @Inject("injector") OkaeriInjector injector;
    private @Inject DependencyResolver resolver;
    private @Inject Plugin plugin;

    /**
     * Configure MMOItems addon
     *
     * @return {@link MMOItemsAddon}
     */
    @Bean
    public MMOItemsAddon configureMMOItems() {
        if (this.resolver.isPlugin("MMOItems")) {
            return this.injector.createInstance(MMOItemsAddonImpl.class);
        } else {
            return this.injector.createInstance(DefaultMMOItemsAddon.class);
        }
    }
}
