package com.qualityplus.assistant.base.factory;

import com.cryptomorin.xseries.XMaterial;
import com.qualityplus.assistant.api.addons.PasterAddon;
import com.qualityplus.assistant.api.dependency.resolver.DependencyResolver;
import com.qualityplus.assistant.base.addons.paster.DefaultPasterAddon;
import com.qualityplus.assistant.base.addons.paster.WorldEdit6;
import com.qualityplus.assistant.base.addons.paster.WorldEdit7;
import eu.okaeri.injector.OkaeriInjector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Bean;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

/**
 * Paster Addon Factory
 */
@Component
public final class PasterFactory {
    private @Inject("injector") OkaeriInjector injector;
    private @Inject DependencyResolver resolver;
    private @Inject Plugin plugin;
    private @Inject Logger logger;

    /**
     * Configure Paster Addon
     *
     * @return {@link PasterAddon}
     */
    @Bean
    private PasterAddon configurePaster() {
        return !this.resolver.isPlugin("WorldEdit") ?
                this.injector.createInstance(DefaultPasterAddon.class) :
                getPaster(XMaterial.getVersion() > 13 ? WorldEdit7.class : WorldEdit6.class);
    }

    private PasterAddon getPaster(final Class<? extends PasterAddon> paster) {
        return this.injector.createInstance(paster);
    }

    private boolean isWorldEdit() {
        return this.resolver.isPlugin("WorldEdit")
                || this.resolver.isPlugin("FastAsyncWorldEdit")
                || this.resolver.isPlugin("AsyncWorldEdit");
    }
}