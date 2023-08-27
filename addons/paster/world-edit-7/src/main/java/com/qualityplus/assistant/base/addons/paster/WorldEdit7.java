package com.qualityplus.assistant.base.addons.paster;

import com.qualityplus.assistant.api.addons.WEPasterAddon;
import com.qualityplus.assistant.api.addons.paster.cuboid.Cuboid;
import com.qualityplus.assistant.api.addons.paster.schematic.Schematic;
import com.qualityplus.assistant.api.addons.paster.session.PasterSession;
import com.qualityplus.assistant.api.dependency.resolver.DependencyResolver;
import com.qualityplus.assistant.base.addons.paster.session.WorldEditSession7;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.bukkit.scheduler.PlatformScheduler;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * WorldEdit 6 Implementation
 */
public final class WorldEdit7 implements WEPasterAddon {
    private @Inject("scheduler") PlatformScheduler scheduler;
    private @Inject DependencyResolver resolver;

    @Override
    public CompletableFuture<PasterSession> pasteSchematic(final Location location, final Schematic schematic) {
        final CompletableFuture<PasterSession> future = new CompletableFuture<>();
        final Runnable pasteTask = () -> {
            final File file = schematic.getFile();
            final ClipboardFormat format = ClipboardFormats.findByFile(file);
            try {
                final ClipboardReader reader = format.getReader(Files.newInputStream(file.toPath()));

                final Clipboard clipboard = reader.read();

                final EditSession editSession = WorldEdit.getInstance()
                        .getEditSessionFactory()
                        .getEditSession(new BukkitWorld(location.getWorld()), -1);

                final Operation operation = new ClipboardHolder(clipboard)
                                            .createPaste(editSession)
                                            .to(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
                                            .ignoreAirBlocks(true)
                                            .build();
                Operations.complete(operation);

                future.complete(new WorldEditSession7(editSession, getCuboid(location, clipboard)));

                Optional.ofNullable(editSession).ifPresent(EditSession::close);

                if (reader != null) {
                    reader.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            } catch (final WorldEditException e) {
                throw new RuntimeException(e);
            }
        };
        if (isAsync(this.resolver)) {
            this.scheduler.runAsync(pasteTask);
        } else {
            this.scheduler.runSync(pasteTask);
        }

        return future;
    }


    private Cuboid getCuboid(final Location baseLoc, final Clipboard cc) {
        try {
            final org.bukkit.World world = baseLoc.getWorld();
            final BlockVector3 to = BlockVector3.at(baseLoc.getX(), baseLoc.getY(), baseLoc.getZ());
            final Region region = cc.getRegion();
            final ClipboardHolder holder = new ClipboardHolder(cc);
            final BlockVector3 clipboardOffset = cc.getRegion().getMinimumPoint().subtract(cc.getOrigin());
            final Vector3 realTo = to.toVector3().add(holder.getTransform().apply(clipboardOffset.toVector3()));
            final Vector3 max = realTo.add(holder.getTransform()
                    .apply(region.getMaximumPoint().subtract(region.getMinimumPoint()).toVector3()));
            final BlockVector3 realVector = realTo.toBlockPoint();
            final BlockVector3 maxVector = max.toBlockPoint();
            final Location maxLocation = new Location(world, realVector.getX(), realVector.getY(), realVector.getZ());
            final Location minLocation = new Location(world, maxVector.getX(), maxVector.getY(), maxVector.getZ());
            return new Cuboid(minLocation, maxLocation);
        } catch (final NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAddonName() {
        return "World Edit 7";
    }
}
