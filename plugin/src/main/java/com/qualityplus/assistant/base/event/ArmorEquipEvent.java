package com.qualityplus.assistant.base.event;

import com.qualityplus.assistant.api.event.PlayerAssistantEvent;
import com.qualityplus.assistant.util.armor.ArmorType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Armor Equip Event
 */
@Getter
@Setter
public final class ArmorEquipEvent extends PlayerAssistantEvent {
    private final EquipMethod method;
    private final ArmorType type;
    private ItemStack oldArmorPiece, newArmorPiece;

    /**
     * All args constructor
     * @param player        {@link Player}
     * @param equipType     {@link EquipMethod}
     * @param type          {@link ArmorType}
     * @param oldArmorPiece {@link ItemStack}
     * @param newArmorPiece {@link ItemStack}
     */
    public ArmorEquipEvent(final Player player, final EquipMethod equipType,
                           final ArmorType type, final ItemStack oldArmorPiece,
                           final ItemStack newArmorPiece) {
        super(player);
        this.oldArmorPiece = oldArmorPiece;
        this.newArmorPiece = newArmorPiece;
        this.method = equipType;
        this.type = type;
    }

    /**
     * Represent the way of equip armors
     */
    public enum EquipMethod {
        /**
         * Equip using shift click
         */
        SHIFT_CLICK,
        /**
         * Equip with drag and drop
         */
        DRAG,
        /**
         * Equip with picking up drop
         */
        PICK_DROP,
        /**
         * Equip trough hot bar
         */
        HOTBAR,
        /**
         * Equip by hot bar swap
         */
        HOTBAR_SWAP,
        /**
         * Equip with dispenser
         */
        DISPENSER,
        /**
         * Equip with broke
         */
        BROKE,
        /**
         * Equip trough death
         */
        DEATH
    }
}