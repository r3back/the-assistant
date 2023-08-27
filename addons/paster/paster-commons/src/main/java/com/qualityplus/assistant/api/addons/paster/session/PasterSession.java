package com.qualityplus.assistant.api.addons.paster.session;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;

/**
 * Schematic past session result
 */
public interface PasterSession {
    /**
     * Undo actions done during paster session
     */
    public void undo();

    /**
     * Set all blocks inside Paster session to specified
     * material
     *
     * @param material {@link Material}
     */
    public void setAllBlocks(final Material material);

    /**
     * Retrieve all paster session blocks
     *
     * @return List of {@link Block}
     */
    public List<Block> getAllBlocks();

    /**
     * Retrieves if location is inside paster session
     * area
     *
     * @param location {@link Location}
     * @return true if it's inside
     */
    public boolean isInside(final Location location);
}
