package com.qualityplus.assistant.base.addons.paster;

import com.qualityplus.assistant.api.addons.WEPasterAddon;
import com.qualityplus.assistant.api.addons.paster.cuboid.Cuboid;
import com.qualityplus.assistant.api.addons.paster.schematic.Schematic;
import com.qualityplus.assistant.api.addons.paster.session.PasterSession;
import com.qualityplus.assistant.api.dependency.resolver.DependencyResolver;
import com.qualityplus.assistant.base.addons.paster.session.WorldEditSession6;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.bukkit.scheduler.PlatformScheduler;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.concurrent.CompletableFuture;

/**
 * WorldEdit 6 Implementation
 */
public final class WorldEdit6 implements WEPasterAddon {
    private @Inject("scheduler") PlatformScheduler scheduler;
    private @Inject DependencyResolver resolver;

    @Override
    public CompletableFuture<PasterSession> pasteSchematic(final Location location, final Schematic schematic) {
        final CompletableFuture<PasterSession> future = new CompletableFuture<>();

        final Runnable pasteTask = () -> {
            try {
                final BukkitWorld world = new BukkitWorld(location.getWorld());
                final CuboidClipboard cuboidClipboard = CuboidClipboard.loadSchematic(schematic.getFile());
                final Vector vector = new Vector(location.getX(), location.getY(), location.getZ());
                final EditSession editSession = new EditSession(world, 999999999);
                cuboidClipboard.paste(editSession, vector, true);
                future.complete(new WorldEditSession6(editSession, getCuboid(location, cuboidClipboard)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        if (isAsync(resolver)) {
            scheduler.runAsync(pasteTask);
        } else {
            scheduler.runSync(pasteTask);
        }

        return future;
    }

    private Cuboid getCuboid(final Location baseLoc, final CuboidClipboard cc) {
        final int height = cc.getHeight();
        final int width = cc.getWidth();
        final int length = cc.getLength();
        final Location endLoc = baseLoc.clone().add(width, height, length);
        final int minX = Math.min(baseLoc.getBlockX(), endLoc.getBlockX());
        final int minY = Math.min(baseLoc.getBlockY(), endLoc.getBlockY());
        final int minZ = Math.min(baseLoc.getBlockZ(), endLoc.getBlockZ());
        final int maxX = Math.max(baseLoc.getBlockX(), endLoc.getBlockX());
        final int maxY = Math.max(baseLoc.getBlockY(), endLoc.getBlockY());
        final int maxZ = Math.max(baseLoc.getBlockZ(), endLoc.getBlockZ());
        final World world = baseLoc.getWorld();
        final Vector vector = cc.getOffset();
        final Location minLocation = (new Location(world, minX, minY, minZ)).add(vector.getX(), vector.getY(), vector.getZ());
        final Location maxLocation = (new Location(world, maxX, maxY, maxZ)).add(vector.getX(), vector.getY(), vector.getZ());
        return new Cuboid(maxLocation, minLocation);
    }

    @Override
    public String getAddonName() {
        return "World Edit 6";
    }
}
