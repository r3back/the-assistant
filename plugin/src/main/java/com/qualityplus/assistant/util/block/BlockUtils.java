package com.qualityplus.assistant.util.block;

import com.cryptomorin.xseries.XMaterial;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

/**
 * Block Utility class
 */
@UtilityClass
public class BlockUtils {
    private final String METADATA_VALUE = "theAssistantPlayerBlock";

    /**
     *
     * @return Block metadata value
     */
    public String getBlockMetadataValue() {
        return METADATA_VALUE;
    }

    /**
     * Retrieves if a block is placed by player
     *
     * @param block {@link Block}
     * @return true if player placed it
     */
    public boolean isPlacedByPlayer(final Block block) {
        return block.hasMetadata(METADATA_VALUE);
    }

    /**
     * Retrieves if a block is null or is air
     *
     * @param block {@link Block}
     * @return true if is not air or null
     */
    public boolean isNull(final Block block) {
        return block == null || block.getType().equals(Material.AIR);
    }

    /**
     * Retrieves if a block is not null or is not air
     *
     * @param block {@link Block}
     * @return true if is not air or null
     */
    public boolean isNotNull(final Block block) {
        return !isNull(block);
    }

    /**
     * Changes block type given a XMaterial
     *
     * @param block    {@link Block}
     * @param material {@link XMaterial}
     */
    public void setBlock(final Block block, final XMaterial material) {
        final Material mat = material.parseMaterial();

        if (mat == null) {
            return;
        }

        block.setType(mat);
    }

    /**
     * Changes block type given a ItemStack
     *
     * @param block     {@link Block}
     * @param itemStack {@link ItemStack}
     */
    public void setBlock(final Block block, final ItemStack itemStack) {
        try {
            final Material mat = itemStack.getType();

            block.setType(mat);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
