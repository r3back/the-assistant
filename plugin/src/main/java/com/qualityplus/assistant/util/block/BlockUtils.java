package com.qualityplus.assistant.util.block;

import com.cryptomorin.xseries.XMaterial;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class BlockUtils {
    public boolean isNull(final Block block) {
        return block == null || block.getType().equals(Material.AIR);
    }

    public boolean isNotNull(final Block block) {
        return !isNull(block);
    }

    public void setBlock(final Block block, final XMaterial material) {
        final Material mat = material.parseMaterial();

        if (mat == null) {
            return;
        }

        block.setType(mat);
    }

    public void setBlock(final Block block, final ItemStack itemStack) {
        try {
            final Material mat = itemStack.getType();

            block.setType(mat);
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }
}
