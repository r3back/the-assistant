package com.qualityplus.assistant.base.factory;

import com.cryptomorin.xseries.XMaterial;
import com.qualityplus.assistant.api.addons.RegionAddon;
import com.qualityplus.assistant.api.dependency.resolver.DependencyResolver;
import com.qualityplus.assistant.base.addons.regions.*;
import eu.okaeri.injector.OkaeriInjector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Bean;
import eu.okaeri.platform.core.annotation.Component;

/**
 * Region addon factory
 */
@Component
public final class RegionFactory {
    private @Inject("injector") OkaeriInjector injector;
    private @Inject DependencyResolver resolver;

    /**
     * Configure region addon
     *
     * @return {@link RegionAddon}
     */
    @Bean
    private RegionAddon configureRegions() {
        if (this.resolver.isPlugin("WorldGuard")) {
            return this.injector.createInstance(getWorldGuard());
        } else if (this.resolver.isPlugin("Residence")) {
            return this.injector.createInstance(ResidenceAddon.class);
        } else if (this.resolver.isPlugin("UltraRegions")) {
            return this.injector.createInstance(UltraRegionsAddon.class);
        } else {
            return this.injector.createInstance(DefaultRegionsAddon.class);
        }
    }

    private Class<? extends RegionAddon> getWorldGuard() {
        return XMaterial.getVersion() > 12 ? WG7Addon.class : WG6Addon.class;
    }
}
