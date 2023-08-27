package com.qualityplus.assistant.listener.armorhandler;

import com.qualityplus.assistant.api.util.BukkitItemUtil;
import com.qualityplus.assistant.base.event.ArmorEquipEvent;
import com.qualityplus.assistant.util.armor.ArmorType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Handler implementation for Shift Click in armor slots
 */
public final class ArmorShiftClickHandler implements ArmorClickHandler {
    private static final ArmorEquipEvent.EquipMethod SHIFT_CLICK_METHOD = ArmorEquipEvent.EquipMethod.SHIFT_CLICK;

    @Override
    public void handle(final ArmorType newArmorType, final InventoryClickEvent event) {

        if (newArmorType == null) {
            return;
        }

        final boolean equipping = event.getRawSlot() != newArmorType.getSlot();
        final PlayerInventory inv = event.getWhoClicked().getInventory();
        final Player player = (Player) event.getWhoClicked();
        final boolean playerIsEquippingArmor = playerIsEquippingArmor(newArmorType, equipping, inv);

        if (!playerIsEquippingArmor) {
            return;
        }

        final ItemStack oldItem = equipping ? null : event.getCurrentItem();
        final ItemStack newItem = equipping ? event.getCurrentItem() : null;
        final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(player, SHIFT_CLICK_METHOD, newArmorType, oldItem, newItem);
        Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);

        if (!armorEquipEvent.isCancelled()) {
            return;
        }

        event.setCancelled(true);

    }

    private boolean playerIsEquippingArmor(final ArmorType type, final boolean equiping, final PlayerInventory inv) {
        final ItemStack itemStack = type.getInventoryFunction().apply(inv);

        return equiping == BukkitItemUtil.isNull(itemStack);
    }

}
