package com.qualityplus.assistant.api.addons;

import com.qualityplus.assistant.api.addons.response.ChunkCheckResponse;
import com.qualityplus.assistant.api.dependency.DependencyPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * World Manager Addon
 */
public interface WorldManagerAddon extends DependencyPlugin {
    /**
     * Return entities in a location chunk
     * @param location {@link Location}
     * @return List of {@link Entity}
     */
    public default List<Entity> getChunkEntities(final Location location) {
        return Arrays.asList(location.getChunk().getEntities());
    }

    /**
     * Retrieves a check response from location chunk
     *
     * @param location {@link Location}
     * @return CompletableFuture of {@link ChunkCheckResponse}
     */
    public default CompletableFuture<ChunkCheckResponse> chunksAreLoaded(final Location location) {
        return CompletableFuture.completedFuture(ChunkCheckResponse.builder()
                .canBeLoaded(true)
                .areLoaded(location.getChunk().isLoaded())
                .build());
    }

    /**
     * Load chunks of a location
     *
     * @param location {@link Location}
     */
    public default void loadChunks(final Location location) {
        location.getChunk().load();
    }

    /**
     * Setup
     */
    public default void setup() {
    }
}
