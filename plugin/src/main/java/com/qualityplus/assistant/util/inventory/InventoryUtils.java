package com.qualityplus.assistant.util.inventory;

import com.qualityplus.assistant.api.util.BukkitItemUtil;
import com.qualityplus.assistant.inventory.background.Background;
import com.qualityplus.assistant.util.itemstack.ItemStackUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Utility class for Inventories
 */
@UtilityClass
public final class InventoryUtils {
    /**
     * Fill Inventory with background items
     *
     * @param inventory  {@link Inventory}
     * @param background {@link Background}
     */
    public static void fillInventory(final Inventory inventory, final Background background) {
        if (background.isUseFiller() && background.getFiller() != null) {
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, ItemStackUtils.makeItem(background.getFiller()));
            }
        }

        if (background.getItems() == null) {
            return;
        }

        for (int slot : background.getItems().keySet()) {
            if (slot >= inventory.getSize()) {
                continue;
            }
            inventory.setItem(slot, ItemStackUtils.makeItem(background.getItems().get(slot)));
        }
    }

    /**
     * Retrieves the amount of an item given an Array of items
     *
     * @param itemStacks     Array of {@link ItemStack}
     * @param itemStackParam {@link ItemStack}
     * @return item amount
     */
    public static int getItemQuantity(final ItemStack[] itemStacks, final ItemStack itemStackParam) {
        int quantity = 0;

        for (ItemStack itemStack : itemStacks) {
            if (itemStack == null) {
                continue;
            }

            final ItemStack tempItemStack = itemStack.clone();

            tempItemStack.setAmount(itemStack.getMaxStackSize());

            if (!tempItemStack.isSimilar(itemStackParam)) {
                continue;
            }

            quantity += itemStack.getAmount();
        }

        return quantity;
    }

    /**
     * Removes specific amount of items from inventory
     *
     * @param inventory   {@link Inventory}
     * @param stack       {@link ItemStack}
     * @param amountParam amount to be removed
     */
    public static void removeItems(final Inventory inventory, final ItemStack stack, final int amountParam) {
        int amount = amountParam;

        for (int i = 0; i < 64; i++) {
            if (amount <= 0 || !inventory.containsAtLeast(stack, amount)) {
                break;
            }

            final ItemStack toRemove = stack.clone();

            toRemove.setAmount(amount);

            amount = inventory.removeItem(toRemove).keySet().stream().findFirst()
                    .orElse(0);
        }
    }

    /**
     * Adds specific amount of items to inventory
     *
     * @param inventory {@link Inventory}
     * @param stack     {@link ItemStack}
     * @param amount    amount to be added
     */
    public static void addItems(final Inventory inventory, final ItemStack stack, final int amount) {
        if (BukkitItemUtil.isNull(stack)) {
            return;
        }

        final ItemStack toAdd = stack.clone();

        toAdd.setAmount(1);

        for (int i = 0; i < amount; i++) {
            inventory.addItem(toAdd.clone());
        }
    }

    /**
     * Removes item from inventory
     *
     * @param inventory {@link Inventory}
     * @param stack     {@link ItemStack}
     */
    public static void removeItems(final Inventory inventory, final ItemStack stack) {
        final int amount = stack.getAmount();

        final ItemStack modified = BukkitItemUtil.getItemWithout(stack, amount);

        removeItems(inventory, modified, amount);
    }
}
