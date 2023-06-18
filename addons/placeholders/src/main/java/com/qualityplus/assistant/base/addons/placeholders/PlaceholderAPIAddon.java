package com.qualityplus.assistant.base.addons.placeholders;

import com.qualityplus.assistant.api.addons.PlaceholdersAddon;
import com.qualityplus.assistant.api.addons.registrable.RegistrableAddon;
import com.qualityplus.assistant.api.addons.replacer.PlaceholderReplaceEvent;
import com.qualityplus.assistant.api.addons.replacer.PlaceholderReplacer;
import eu.okaeri.injector.annotation.Inject;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * PlaceholdersAPI Implementation
 */
public final class PlaceholderAPIAddon extends PlaceholderExpansion implements PlaceholdersAddon, RegistrableAddon {
    private final Map<String, PlaceholderReplacer> placeholdersMap = new HashMap<>();
    private @Inject Plugin plugin;

    @Override
    public @NotNull String getIdentifier() {
        return "thecore";
    }

    @Override
    public @NotNull String getAuthor() {
        return this.plugin.getDescription().getAuthors().stream()
                .findFirst()
                .map(Object::toString)
                .orElse("qualityplus");
    }

    @Override
    public @NotNull String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public void registerAddon() {
        super.register();
    }

    @Override
    public String getAddonName() {
        return "PlaceholderAPI";
    }

    @Override
    public void registerPlaceholders(final String identifier, final PlaceholderReplacer replacer) {
        this.placeholdersMap.put(identifier, replacer);
    }

    @Override
    public String onPlaceholderRequest(final Player player, final @NotNull String identifier) {
        if (player == null) {
            return "";
        }

        return this.placeholdersMap.entrySet().stream()
                .filter(entry -> entry.getKey().equals(identifier))
                .map(entry -> entry.getValue().onPlaceholderReplace(new PlaceholderReplaceEvent(player)))
                .findFirst()
                .orElse(null);
    }
}
