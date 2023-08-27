package com.qualityplus.assistant.util.armor;

import com.qualityplus.assistant.api.util.BukkitItemUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.function.Function;

/**
 * Represent Armor Types
 */
@Getter
@RequiredArgsConstructor
public enum ArmorType {
    /**
     * Helmet
     */
    HELMET(PlayerInventory::getHelmet, 5),
    /**
     * ChestPlate
     */
    CHESTPLATE(PlayerInventory::getChestplate, 6),
    /**
     * Leggings
     */
    LEGGINGS(PlayerInventory::getLeggings, 7),
    /**
     * Boots
     */
    BOOTS(PlayerInventory::getBoots, 8);

    private final Function<PlayerInventory, ItemStack> inventoryFunction;
    private final int slot;

    /**
     * Converts {@link ItemStack} to {@link ArmorType}
     *
     * @param itemStack {@link ItemStack}
     * @return {@link ArmorType}
     */
    public static ArmorType matchType(final ItemStack itemStack) {
        if (BukkitItemUtil.isNull(itemStack)) {
            return null;
        }

        final String type = itemStack.getType().name();

        if (type.endsWith("_HELMET") || type.endsWith("_SKULL") || type.endsWith("_HEAD")) {
            return HELMET;
        } else if (type.endsWith("_CHESTPLATE") || type.equals("ELYTRA")) {
            return CHESTPLATE;
        } else if (type.endsWith("_LEGGINGS")) {
            return LEGGINGS;
        } else if (type.endsWith("_BOOTS")) {
            return BOOTS;
        } else {
            return null;
        }
    }
}
