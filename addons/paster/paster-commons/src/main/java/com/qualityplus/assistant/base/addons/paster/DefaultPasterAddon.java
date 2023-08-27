package com.qualityplus.assistant.base.addons.paster;

import com.qualityplus.assistant.api.addons.PasterAddon;
import com.qualityplus.assistant.api.addons.paster.schematic.Schematic;
import com.qualityplus.assistant.api.addons.paster.session.PasterSession;
import org.bukkit.Location;

import java.util.concurrent.CompletableFuture;

/**
 * Default Paster Addon implementation
 */
public final class DefaultPasterAddon implements PasterAddon {
    @Override
    public String getAddonName() {
        return null;
    }

    @Override
    public CompletableFuture<PasterSession> pasteSchematic(final Location location, final Schematic schematic) {
        return new CompletableFuture<>();
    }
}
