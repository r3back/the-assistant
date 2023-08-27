package com.qualityplus.assistant.listener.armorhandler;

import com.qualityplus.assistant.api.util.BukkitItemUtil;
import com.qualityplus.assistant.base.event.ArmorEquipEvent;
import com.qualityplus.assistant.util.armor.ArmorType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

/**
 * Handler implementation for Normal Click in armor slots
 */
public final class NormalArmorClickHandler implements ArmorClickHandler {
    private static final ArmorEquipEvent.EquipMethod SHIFT_CLICK_METHOD = ArmorEquipEvent.EquipMethod.SHIFT_CLICK;

    @Override
    public void handle(final ArmorType newArmorTypeParam, final InventoryClickEvent event) {
        final boolean numberkey = event.getClick().equals(ClickType.NUMBER_KEY);
        ArmorType newArmorType = newArmorTypeParam;
        ItemStack newArmorPiece = event.getCursor();
        ItemStack oldArmorPiece = event.getCurrentItem();
        if (numberkey) {
            if (event.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                final ItemStack hotbarItem = event.getClickedInventory().getItem(event.getHotbarButton());
                if (!BukkitItemUtil.isNull(hotbarItem)) {
                    newArmorType = ArmorType.matchType(hotbarItem);
                    newArmorPiece = hotbarItem;
                    oldArmorPiece = event.getClickedInventory().getItem(event.getSlot());
                } else {
                    newArmorType = ArmorType.matchType(!BukkitItemUtil.isNull(event.getCurrentItem())
                            ? event.getCurrentItem() : event.getCursor());
                }
            }
        } else {
            if (BukkitItemUtil.isNull(event.getCursor()) && !BukkitItemUtil.isNull(event.getCurrentItem())) {
                newArmorType = ArmorType.matchType(event.getCurrentItem());
            }
        }
        if (newArmorType != null && event.getRawSlot() == newArmorType.getSlot()) {
            ArmorEquipEvent.EquipMethod method = ArmorEquipEvent.EquipMethod.PICK_DROP;
            if (event.getAction().equals(InventoryAction.HOTBAR_SWAP) || numberkey) {
                method = ArmorEquipEvent.EquipMethod.HOTBAR_SWAP;
            }
            final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) event.getWhoClicked(), method,
                    newArmorType, oldArmorPiece, newArmorPiece);
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }

    }

}
