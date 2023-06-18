package com.qualityplus.assistant.api.gui;

import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Interface for closable inventories
 */
public interface ClosableInventory {
    /**
     * Used to handle inventory close event
     *
     * @param event {@link InventoryCloseEvent}
     */
    public void onInventoryClose(final InventoryCloseEvent event);
}
