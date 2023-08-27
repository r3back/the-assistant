package com.qualityplus.assistant.listener.armorhandler;

import com.qualityplus.assistant.util.armor.ArmorType;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Handles when a player equips armor
 */
public interface ArmorClickHandler {
    /**
     *
     * @param newArmorType {@link ArmorType}
     * @param event        {@link InventoryClickEvent}
     */
    public void handle(final ArmorType newArmorType, final InventoryClickEvent event);
}
