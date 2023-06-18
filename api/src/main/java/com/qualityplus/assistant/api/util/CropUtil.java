package com.qualityplus.assistant.api.util;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.ImmutableMap;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Map;

/**
 * Utility class to handle crops
 */
@UtilityClass
public class CropUtil {
    private final Map<XMaterial, Integer> MAX_AGE = ImmutableMap.<XMaterial, Integer>builder()
            .put(XMaterial.WHEAT, 7)
            .put(XMaterial.CARROTS, 7)
            .put(XMaterial.POTATOES, 3)
            .put(XMaterial.NETHER_WART, 3)
            .build();

    /**
     * Retrieves max block age by block
     *
     * @param block {@link Block}
     * @return max block age
     */
    public int getMaxAge(final Block block) {
        if (block == null) {
            return 0;
        }

        return getMaxAge(block.getType());
    }

    /**
     * Retrieves max block age by material
     * @param material {@link Material}
     * @return max material age
     */
    public int getMaxAge(final Material material) {
        if (material == null) {
            return 0;
        }

        final XMaterial xmat = XMaterial.matchXMaterial(material);

        return MAX_AGE.getOrDefault(xmat, 0);
    }

    /**
     * Converts {@link XMaterial} to {@link Material}
     *
     * @param material {@link XMaterial}
     * @return {@link Material}
     */
    public Material cropFromXMaterial(final XMaterial material) {
        if (XMaterial.getVersion() > 13) {
            return material.parseMaterial();
        } else {
            try {
                return Material.valueOf(material.getLegacy()[0]);
            }catch (Exception e) {
                return null;
            }
        }
    }
}
