package com.qualityplus.assistant.api.addons.replacer;

/**
 * Placeholder call back
 */
@FunctionalInterface
public interface PlaceholderReplacer {
    /**
     * Callback handler
     *
     * @param event {@link PlaceholderReplaceEvent}
     * @return string after callback process
     */
    public String onPlaceholderReplace(final PlaceholderReplaceEvent event);
}
