package com.qualityplus.assistant.base.factory;

import com.qualityplus.assistant.api.addons.PlaceholdersAddon;
import com.qualityplus.assistant.api.dependency.resolver.DependencyResolver;
import com.qualityplus.assistant.base.addons.placeholders.DefaultPlaceholdersAddon;
import com.qualityplus.assistant.base.addons.placeholders.MVDWPlaceholderAddon;
import com.qualityplus.assistant.base.addons.placeholders.PlaceholderAPIAddon;
import eu.okaeri.injector.OkaeriInjector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Bean;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.plugin.Plugin;

/**
 * Placeholders Addon Factory
 */
@Component
public final class PlaceholderFactory {
    private @Inject("injector") OkaeriInjector injector;
    private @Inject DependencyResolver resolver;
    private @Inject Plugin plugin;

    /**
     * Configure Placeholders addon
     *
     * @return {@link PlaceholdersAddon}
     */
    @Bean
    public PlaceholdersAddon configurePlaceholders() {
        if (this.resolver.isPlugin("PlaceholderAPI")) {
            return this.injector.createInstance(PlaceholderAPIAddon.class);
        } else if (resolver.isPlugin("MVdWPlaceholderAPI")) {
            return this.injector.createInstance(MVDWPlaceholderAddon.class);
        } else {
            return this.injector.createInstance(DefaultPlaceholdersAddon.class);
        }
    }
}