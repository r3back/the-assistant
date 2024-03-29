package com.qualityplus.assistant.base.addons.worldmanager;

import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import com.qualityplus.assistant.api.addons.WorldManagerAddon;
import com.qualityplus.assistant.api.addons.response.ChunkCheckResponse;
import com.qualityplus.assistant.api.config.ConfigSlimeWorldManager;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Slime World Manager Implementation
 */
public final class SlimeWorldAddon implements WorldManagerAddon {
    private static final SlimePropertyMap PROPERTIES = new SlimePropertyMap();
    private @Inject ConfigSlimeWorldManager configSlimeWorldManager;
    private SlimePlugin slimePlugin;
    private SlimeLoader loader;


    @Override
    public List<Entity> getChunkEntities(final Location location) {
        return null;
    }

    @Override
    public CompletableFuture<ChunkCheckResponse> chunksAreLoaded(final Location location) {

        final String name = location.getWorld().getName();

        if (Bukkit.getWorld(name) != null) {
            return WorldManagerAddon.super.chunksAreLoaded(location);
        }

        final SlimeWorld slimeWorld = this.slimePlugin.getWorld(this.loader, location.getWorld().getName());

        if (slimeWorld == null) {
            return CompletableFuture.completedFuture(ChunkCheckResponse.builder()
                    .areLoaded(false)
                    .canBeLoaded(false)
                    .build());
        }


        final CompletableFuture<ChunkCheckResponse> response = new CompletableFuture<>();

        this.slimePlugin.asyncLoadWorld(this.loader, name, false, PROPERTIES).thenAccept(world -> {
            if (!world.isPresent()) {
                return;
            }

            response.complete(ChunkCheckResponse.builder()
                    .areLoaded(true)
                    .canBeLoaded(true)
                    .build());
        });

        return response;
    }

    @Override
    public void loadChunks(final Location location) {

    }

    @Override
    public void setup() {
        try {
            this.slimePlugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");

            if (this.slimePlugin == null) {
                throw new NullPointerException();
            }

            this.loader = this.slimePlugin.getLoader(this.configSlimeWorldManager.getSlimeWorldManagerSource());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getAddonName() {
        return "Slime World Manager";
    }
}
