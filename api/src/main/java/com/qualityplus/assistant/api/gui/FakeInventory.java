package com.qualityplus.assistant.api.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

/**
 * Fake Inventory
 */
public interface FakeInventory {
    /**
     * Remove a specific amount of certain itemstack from inventory
     *
     * @param itemStack {@link ItemStack}
     * @param amount    amount to be removed
     */
    public void removeItems(final ItemStack itemStack, final int amount);

    /**
     * Retrieves the Item and the amount of it from inventory
     *
     * @return Map of {@link ItemStack} and {@link Integer}
     */
    public Map<ItemStack, Integer> getItemsWithAmount();

    /**
     * Set Items in inventory
     *
     * @param items Map of {@link ItemStack} and {@link Integer}
     */
    public void setItems(final Map<Integer, ItemStack> items);

    /**
     * Retrieves inventory slots with its respectively item
     *
     * @return Map of {@link Integer} and {@link ItemStack}
     */
    public Map<Integer, ItemStack> getItems();

    /**
     * Return items in inventory as an array
     *
     * @return Array of {@link ItemStack}
     */
    public ItemStack[] getItemsArray();

    /**
     * Reduces last inventory item amount by 1
     *
     * @return {@link ItemStack}
     */
    public ItemStack removeOneFromLastItem();

    /**
     * Retrieves Bukkit Inventory
     *
     * @return {@link Inventory}
     */
    public Inventory getInventory();

    /**
     * Retrieves empty slots amount
     *
     * @return empty slots amount
     */
    public int getEmptySlots();

    /**
     * Removes all inventory items
     */
    public void removeItems();

    /**
     * Retrieve inventory max slots
     *
     * @return inventory max slots
     */
    public int getSlots();

    /**
     * Remove inventory from cache
     */
    public void remove();

    /**
     * Retrieves fake inventory entity id
     *
     * @return entity id
     */
    public int getEntityId();

}
