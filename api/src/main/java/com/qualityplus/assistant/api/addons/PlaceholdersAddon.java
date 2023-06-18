package com.qualityplus.assistant.api.addons;

import com.qualityplus.assistant.api.addons.replacer.PlaceholderReplacer;
import com.qualityplus.assistant.api.dependency.DependencyPlugin;

/**
 * Placeholder Addon
 */
public interface PlaceholdersAddon extends DependencyPlugin {
    /**
     * Register a placeholder with custom placeholder replacer
     *
     * @param identifier placeholder key
     * @param replacer   {@link PlaceholderReplacer}
     */
    public void registerPlaceholders(final String identifier, final PlaceholderReplacer replacer);
}
