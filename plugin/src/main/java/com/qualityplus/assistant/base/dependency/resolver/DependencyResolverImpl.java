package com.qualityplus.assistant.base.dependency.resolver;

import com.qualityplus.assistant.api.dependency.resolver.DependencyResolver;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.plugin.Plugin;

/**
 * Dependency Resolver implementation
 */
@Component
public final class DependencyResolverImpl implements DependencyResolver {
    private @Inject Plugin plugin;

    @Override
    public boolean isPlugin(final String name) {
        return this.plugin.getServer().getPluginManager().getPlugin(name) != null;
    }
}
