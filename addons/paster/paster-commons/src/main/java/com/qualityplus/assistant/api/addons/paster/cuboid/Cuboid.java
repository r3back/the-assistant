package com.qualityplus.assistant.api.addons.paster.cuboid;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * Cuboid Structure Representation
 */
public final class Cuboid implements Iterable<Block>, ConfigurationSerializable {
    private String worldName = "";
    private int x1 = 0, y1 = 0, z1 = 0;
    private int x2 = 0, y2 = 0, z2 = 0;
    private final Location maxLocation;
    private final Location minLocation;

    /**
     * Constructor with one argument
     *
     * @param location {@link Location}
     */
    public Cuboid(final Location location) {
        this(location, location);
    }

    public Cuboid(final Location maxLocation, final Location minLocation) {
        Validate.notNull(maxLocation);
        Validate.notNull(minLocation);

        if (!maxLocation.getWorld().getUID().equals(minLocation.getWorld().getUID())) {
            throw new IllegalArgumentException("Location 1 must be in the same world as Location 2!");
        }

        this.worldName = maxLocation.getWorld().getName();

        this.x1 = Math.min((int) maxLocation.getX(), (int) minLocation.getX());
        this.y1 = Math.min((int) maxLocation.getY(), (int) minLocation.getY());
        this.z1 = Math.min((int) maxLocation.getZ(), (int) minLocation.getZ());

        this.x2 = Math.max((int) maxLocation.getX(), (int) minLocation.getX());
        this.y2 = Math.max((int) maxLocation.getY(), (int) minLocation.getY());
        this.z2 = Math.max((int) maxLocation.getZ(), (int) minLocation.getZ());

        this.maxLocation = maxLocation;
        this.minLocation = minLocation;
    }

    /**
     * Retrieve a list of blocks in cuboid area
     *
     * @return List of {@link Block}
     */
    public List<Block> getBlocks() {
        final List<Block> blockList = new ArrayList<>();
        final World cuboidWorld = this.getWorld();
        for (int x = this.x1; x <= this.x2; x++) {
            for (int y = this.y1; y <= this.y2; y++) {
                for (int z = this.z1; z <= this.z2; z++) {
                    blockList.add(cuboidWorld.getBlockAt(x, y, z));
                }
            }
        }
        return blockList;
    }

    /**
     * Retrieves Cuboid world
     *
     * @return {@link World}
     */
    public World getWorld() {
        World cuboidWorld = Bukkit.getServer().getWorld(this.worldName);
        if (cuboidWorld == null) {
            cuboidWorld = Bukkit.getServer().createWorld(WorldCreator.name(this.worldName));
        }
        return cuboidWorld;
    }

    /**
     * Serializes cuboid
     *
     * @return Map of {@link String} and {@link Object}
     */
    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> serializedCuboid = new HashMap<String, Object>();
        serializedCuboid.put("World", this.worldName);
        serializedCuboid.put("X1", this.x1);
        serializedCuboid.put("Y1", this.y1);
        serializedCuboid.put("Z1", this.z1);
        serializedCuboid.put("X2", this.x2);
        serializedCuboid.put("Y2", this.y2);
        serializedCuboid.put("Z2", this.z2);
        return serializedCuboid;
    }

    /**
     * Retrieves iterator for cuboid blocks
     *
     * @return ListIterator of {@link Block}
     */
    @Override
    public ListIterator<Block> iterator() {
        return this.getBlocks().listIterator();
    }

    /**
     * Retrieves if a location is inside cuboid
     *
     * @param location {@link Location}
     * @return true if it's inside
     */
    public boolean isInside(final Location location) {
        final Vector v1 = getVector(this.minLocation);

        final Vector v2 = getVector(this.maxLocation);

        return isInAABB(location.toVector(), v1, v2);
    }

    private Vector getVector(final Location location) {
        return new Vector(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    private boolean isInAABB(final org.bukkit.util.Vector vector, final org.bukkit.util.Vector min, final Vector max) {
        final int x = vector.getBlockX();
        final int y = vector.getBlockY();
        final int z = vector.getBlockZ();

        return x >= min.getBlockX()
                && x <= max.getBlockX()
                && y >= min.getBlockY()
                && y <= max.getBlockY()
                && z >= min.getBlockZ()
                && z <= max.getBlockZ();
    }
}