package com.qualityplus.assistant.util.faster;

import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import lombok.experimental.UtilityClass;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * Utility class to instance quickly {@link ItemStack}
 */
@UtilityClass
public class FastStack extends OkaeriConfig {
    /**
     * Retrieves an item given an XMaterial
     *
     * @param material {@link XMaterial}
     * @return {@link ItemStack}
     */
    public ItemStack fast(final XMaterial material) {
        return fast(material, 1);
    }

    /**
     * Retrieves an item given an XMaterial
     * and a specific amount
     *
     * @param material {@link XMaterial}
     * @param amount   item amount
     * @return {@link ItemStack}
     */
    public ItemStack fast(final XMaterial material, final int amount) {
        return new ItemStack(material.parseMaterial(), amount);
    }

    /**
     * Retrieves an item given an XMaterial
     * and a specific color
     *
     * @param material {@link XMaterial}
     * @param color    {@link Color}
     * @return {@link ItemStack}
     */
    public ItemStack fastWithColor(final XMaterial material, final Color color) {
        final ItemStack item = material.parseItem();

        if (color == null) {
            return item;
        }

        final ItemMeta meta = item.getItemMeta();
        final LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
        leatherArmorMeta.setColor(color);
        item.setItemMeta(leatherArmorMeta);

        return item;
    }
}
