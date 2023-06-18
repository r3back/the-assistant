package com.qualityplus.assistant.api.addons;

import com.qualityplus.assistant.api.addons.paster.schematic.Schematic;
import com.qualityplus.assistant.api.addons.paster.session.PasterSession;
import com.qualityplus.assistant.api.dependency.DependencyPlugin;
import org.bukkit.Location;

import java.util.concurrent.CompletableFuture;

/**
 * Paster Addon interface
 */
public interface PasterAddon extends DependencyPlugin {
    /**
     * Paste World edit schematic in specific location
     *
     * @param location  {@link Location}
     * @param schematic {@link Schematic}
     * @return CompletableFuture of {@link PasterSession}
     */
    public CompletableFuture<PasterSession> pasteSchematic(final Location location, final Schematic schematic);
}
