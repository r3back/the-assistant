package com.qualityplus.assistant.api.dependency;

/**
 * Dependency Plugin
 */
public interface DependencyPlugin {
    /**
     * Retrieves Addon name
     *
     * @return addon name
     */
    public String getAddonName();

    /**
     * Retrieves Addon version
     *
     * @return addon version
     */
    public default String getVersion() {
        return "1.0";
    }

    /**
     * Retrieves if addon is enabled
     *
     * @return true if it's enabled
     */
    public default boolean isEnabled() {
        return getAddonName() != null;
    }
}
