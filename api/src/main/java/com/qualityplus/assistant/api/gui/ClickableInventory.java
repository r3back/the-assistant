package com.qualityplus.assistant.api.gui;

import com.qualityplus.assistant.api.util.IPlaceholder;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

/**
 * Interface to handle inventories
 *
 * @param <T> Generic Item type
 * @param <B> Generic Background
 */
public interface ClickableInventory<T, B> {
    /**
     * Handles inventory click event
     * @param event {@link InventoryClickEvent}
     */
    public void onInventoryClick(final InventoryClickEvent event);

    /**
     * Set item in inventory with specific lore wrapper
     * @param item        Generic Item
     * @param loreWrapper {@link LoreWrapper}
     */
    public void setItem(final T item, final LoreWrapper loreWrapper);

    /**
     * Set item in inventory with specific lore wrapper and
     * parsed placeholders
     *
     * @param item         Generic Item
     * @param placeholders List of {@link IPlaceholder}
     * @param loreWrapper  {@link LoreWrapper}
     */
    public void setItem(final T item, final List<IPlaceholder> placeholders,
                        final LoreWrapper loreWrapper);

    /**
     * Set item in inventory
     *
     * @param item Generic Item
     */
    public void setItem(final T item);

    /**
     * Set item in inventory with pased placeholders
     *
     * @param item         Generic Item
     * @param placeholders List of {@link IPlaceholder}
     */
    public void setItem(final T item, final List<IPlaceholder> placeholders);

    /**
     * Retrieves if is clicking decoration item
     *
     * @param clickedSlot clicked slot
     * @param background  Generic Inventory background
     * @return true if it"s clicking decoration
     */
    public boolean isClickingDecoration(final Integer clickedSlot, final B background);
}
