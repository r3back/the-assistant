package com.qualityplus.assistant.base.addons.worldmanager;

import com.qualityplus.assistant.api.addons.WorldManagerAddon;
import com.qualityplus.assistant.api.addons.response.ChunkCheckResponse;
import com.qualityplus.assistant.api.config.ConfigSlimeWorldManager;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Slime World Manager Implementation
 */
@Deprecated
public final class SlimeWorldAddon implements WorldManagerAddon {
    private @Inject ConfigSlimeWorldManager configSlimeWorldManager;

    @Override
    public List<Entity> getChunkEntities(final Location location) {
        return null;
    }

    @Override
    public CompletableFuture<ChunkCheckResponse> chunksAreLoaded(final Location location) {
        final CompletableFuture<ChunkCheckResponse> response = new CompletableFuture<>();
        response.complete(null);
        return response;
    }

    @Override
    public void loadChunks(final Location location) {

    }

    @Override
    public String getAddonName() {
        return "Slime World Manager";
    }
}
