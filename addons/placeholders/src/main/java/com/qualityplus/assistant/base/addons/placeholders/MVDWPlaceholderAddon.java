package com.qualityplus.assistant.base.addons.placeholders;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import com.qualityplus.assistant.api.addons.PlaceholdersAddon;
import com.qualityplus.assistant.api.addons.replacer.PlaceholderReplaceEvent;
import com.qualityplus.assistant.api.addons.replacer.PlaceholderReplacer;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

/**
 * MVDWPlaceholders Implementation
 */
public final class MVDWPlaceholderAddon implements PlaceholdersAddon {
    private @Inject Plugin plugin;

    @Override
    public String getAddonName() {
        return "MVdWPlaceholderAPI";
    }

    @Override
    public void registerPlaceholders(final String identifier, final PlaceholderReplacer replacer) {
        PlaceholderAPI.registerPlaceholder(this.plugin, "thecore_" + identifier, e ->
                Optional.ofNullable(e.getPlayer())
                .map(p -> replacer.onPlaceholderReplace(new PlaceholderReplaceEvent(p)))
                .orElse("0"));
    }
}
