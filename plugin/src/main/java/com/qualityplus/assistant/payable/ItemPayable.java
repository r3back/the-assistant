package com.qualityplus.assistant.payable;

import com.cryptomorin.xseries.XMaterial;
import com.qualityplus.assistant.util.inventory.InventoryUtils;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * Represents an item that needs to be paid with XMaterial
 */
public interface ItemPayable {
    /**
     * Retrieves a map with the materials required to afford
     *
     * @return Map of {@link XMaterial} and {@link Integer}
     */
    public Map<XMaterial, Integer> getItemCost();

    /**
     * Retrieves if a player can afford the item
     *
     * @param player {@link Player}
     * @return true if can afford the item
     */
    public default boolean canPayItems(final Player player) {
        for (final XMaterial material : getItemCost().keySet()) {
            final int amount = getItemCost().get(material);

            final int playerAmount = InventoryUtils.getItemQuantity(
                    player.getInventory().getContents(),
                    material.parseItem());

            if (amount > playerAmount) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes required items from player inventory
     *
     * @param player {@link Player}
     */
    public default void payItems(final Player player) {
        getItemCost()
                .keySet()
                .forEach(material -> InventoryUtils.removeItems(
                        player.getInventory(),
                        material.parseItem(),
                        getItemCost().get(material))
                );
    }
}
