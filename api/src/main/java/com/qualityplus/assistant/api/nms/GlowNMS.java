package com.qualityplus.assistant.api.nms;

import org.bukkit.inventory.ItemStack;

/**
 * NMS to handle items glow
 */
public interface GlowNMS {
    /**
     * Return item with glow
     *
     * @param itemStack {@link ItemStack}
     * @return {@link ItemStack}
     */
    public ItemStack getItemWithGlow(final ItemStack itemStack);
}
