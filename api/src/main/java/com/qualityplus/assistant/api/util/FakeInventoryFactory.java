package com.qualityplus.assistant.api.util;

import com.qualityplus.assistant.api.gui.fake.FakeGUI;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Utility class for fake inventories
 */
@UtilityClass
public class FakeInventoryFactory {
    /**
     * Retrieves an inventory with specific size
     *
     * @param maxSlots max inventory slots
     * @return {@link Inventory}
     */
    public Inventory getInventoryWithSize(final int maxSlots) {
        return new FakeGUI(maxSlots).getInventory();
    }

    /**
     * Retrieves an inventory with given items
     *
     * @param contents Array of {@link ItemStack}
     * @return {@link Inventory}
     */
    public Inventory getInventoryWithItems(final ItemStack[] contents) {
        return new FakeGUI(contents, 0).getInventory();
    }

    /**
     * Retrieves an inventory with given items and max slots amount
     * @param contents Array of {@link ItemStack}
     * @param maxSlots max slots amount
     * @return {@link Inventory}
     */
    public Inventory getInventoryWithSize(final ItemStack[] contents, final int maxSlots) {
        return new FakeGUI(contents, maxSlots).getInventory();
    }
}
