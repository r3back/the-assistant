package com.qualityplus.assistant.base.addons.placeholders;

import com.qualityplus.assistant.api.addons.PlaceholdersAddon;
import com.qualityplus.assistant.api.addons.replacer.PlaceholderReplacer;

/**
 * Default Placeholders Addon Implementation
 */
public final class DefaultPlaceholdersAddon implements PlaceholdersAddon {
    @Override
    public String getAddonName() {
        return null;
    }

    @Override
    public void registerPlaceholders(final String identifier, final PlaceholderReplacer replacer) {

    }
}
