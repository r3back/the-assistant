package com.qualityplus.assistant.api.addons;

import com.qualityplus.assistant.api.dependency.resolver.DependencyResolver;

/**
 * World Edit Paster Addon
 */
public interface WEPasterAddon extends PasterAddon {
    /**
     * Retrieves is World Edit is async
     *
     * @param resolver {@link DependencyResolver}
     * @return true if World edit is async
     */
    public default boolean isAsync(final DependencyResolver resolver) {
        return resolver.isPlugin("FastAsyncWorldEdit") || resolver.isPlugin("AsyncWorldEdit");
    }
}
