package com.qualityplus.assistant.api.dependency.resolver;

/**
 * Dependency Resolver
 */
public interface DependencyResolver {
    /**
     * Retrieves is a plugin is present
     *
     * @param name Plugin name
     * @return true if plugin is present
     */
    public boolean isPlugin(final String name);
}
