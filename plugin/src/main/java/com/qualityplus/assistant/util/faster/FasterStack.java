package com.qualityplus.assistant.util.faster;

import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import lombok.experimental.UtilityClass;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

@UtilityClass
public class FasterStack extends OkaeriConfig {
    public ItemStack fast(final XMaterial material) {
        return fast(material, 1);
    }

    public ItemStack fast(final XMaterial material, final int amount) {
        return new ItemStack(material.parseMaterial(), amount);
    }

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
