package com.qualityplus.assistant.listener;

import com.qualityplus.assistant.api.util.BukkitItemUtil;
import com.qualityplus.assistant.base.event.ArmorEquipEvent;
import com.qualityplus.assistant.base.event.ArmorEquipEvent.EquipMethod;
import com.qualityplus.assistant.util.armor.ArmorType;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Armor Listener
 *
 * TODO refactor or remove this ASAP
 */
@Component
public final class ArmorListener implements Listener {
    private static final List<String> BLOCKED_MATERIALS = Collections.singletonList("TRAPDOOR");

    /**
     *
     * @param e {@link InventoryClickEvent}
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public final void inventoryClick(final InventoryClickEvent e) {
        boolean shift = false, numberkey = false;
        if (e.getAction() == InventoryAction.NOTHING) {
            return;
        }
        if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
            shift = true;
        }
        if (e.getClick().equals(ClickType.NUMBER_KEY)) {
            numberkey = true;
        }
        if (e.getSlotType() != InventoryType.SlotType.ARMOR
                && e.getSlotType() != InventoryType.SlotType.QUICKBAR
                && e.getSlotType() != InventoryType.SlotType.CONTAINER) {
            return;
        }
        if (e.getClickedInventory() != null && !e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
            return;
        }
        if (!e.getInventory().getType().equals(InventoryType.CRAFTING)
                && !e.getInventory().getType().equals(InventoryType.PLAYER)) {
            return;
        }
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        ArmorType newArmorType = ArmorType.matchType(shift ? e.getCurrentItem() : e.getCursor());
        if (!shift && newArmorType != null && e.getRawSlot() != newArmorType.getSlot()) {
            return;
        }
        if (shift) {
            newArmorType = ArmorType.matchType(e.getCurrentItem());
            if (newArmorType != null) {
                boolean equipping = e.getRawSlot() != newArmorType.getSlot();
                PlayerInventory inv = e.getWhoClicked().getInventory();
                if (newArmorType.equals(ArmorType.HELMET) && (equipping == BukkitItemUtil.isNull(inv.getHelmet()))
                        || newArmorType.equals(ArmorType.CHESTPLATE) && (equipping == BukkitItemUtil.isNull(inv.getChestplate()))
                        || newArmorType.equals(ArmorType.LEGGINGS) && (equipping == BukkitItemUtil.isNull(inv.getLeggings()))
                        || newArmorType.equals(ArmorType.BOOTS) && (equipping == BukkitItemUtil.isNull(inv.getBoots()))) {
                    ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) e.getWhoClicked(),
                            EquipMethod.SHIFT_CLICK, newArmorType, equipping ? null
                            : e.getCurrentItem(), equipping ? e.getCurrentItem() : null);
                    Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                    if (armorEquipEvent.isCancelled()) {
                        e.setCancelled(true);
                    }
                }
            }
        } else {
            ItemStack newArmorPiece = e.getCursor();
            ItemStack oldArmorPiece = e.getCurrentItem();
            if (numberkey) {
                if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                    ItemStack hotbarItem = e.getClickedInventory().getItem(e.getHotbarButton());
                    if (!BukkitItemUtil.isNull(hotbarItem)) {
                        newArmorType = ArmorType.matchType(hotbarItem);
                        newArmorPiece = hotbarItem;
                        oldArmorPiece = e.getClickedInventory().getItem(e.getSlot());
                    } else {
                        newArmorType = ArmorType.matchType(!BukkitItemUtil.isNull(e.getCurrentItem())
                                ? e.getCurrentItem() : e.getCursor());
                    }
                }
            } else {
                if (BukkitItemUtil.isNull(e.getCursor()) && !BukkitItemUtil.isNull(e.getCurrentItem()))
                    newArmorType = ArmorType.matchType(e.getCurrentItem());
            }
            if (newArmorType != null && e.getRawSlot() == newArmorType.getSlot()) {
                EquipMethod method = EquipMethod.PICK_DROP;
                if (e.getAction().equals(InventoryAction.HOTBAR_SWAP) || numberkey) method = EquipMethod.HOTBAR_SWAP;
                ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) e.getWhoClicked(), method,
                        newArmorType, oldArmorPiece, newArmorPiece);
                Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                if (armorEquipEvent.isCancelled())
                    e.setCancelled(true);
            }
        }
    }

    /**
     *
     * @param e {@link PlayerInteractEvent}
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerInteractEvent(final PlayerInteractEvent e) {
        if (e.useItemInHand().equals(Event.Result.DENY)) {
            return;
        }
        if (e.getAction() == Action.PHYSICAL) {
            return;
        }
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final Player player = e.getPlayer();
            if (!e.useInteractedBlock().equals(Event.Result.DENY)) {
                if (e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK && !player.isSneaking()) {
                    // Having both of these checks is useless, might as well do it though.
                    final Material mat = e.getClickedBlock().getType();
                    for (String s : BLOCKED_MATERIALS) {
                        if (mat.name().equalsIgnoreCase(s) || mat.name().contains(s.toUpperCase())) {
                            return;
                        }
                    }
                }
            }
            final ArmorType newArmorType = ArmorType.matchType(e.getItem());
            if (newArmorType != null) {
                final PlayerInventory inv = e.getPlayer().getInventory();
                if (newArmorType.equals(ArmorType.HELMET)
                        && BukkitItemUtil.isNull(inv.getHelmet())
                        || newArmorType.equals(ArmorType.CHESTPLATE)
                        && BukkitItemUtil.isNull(inv.getChestplate())
                        || newArmorType.equals(ArmorType.LEGGINGS)
                        && BukkitItemUtil.isNull(inv.getLeggings())
                        || newArmorType.equals(ArmorType.BOOTS)
                        && BukkitItemUtil.isNull(inv.getBoots())) {
                    final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(e.getPlayer(),
                            EquipMethod.HOTBAR, ArmorType.matchType(e.getItem()), null, e.getItem());
                    Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                    if (armorEquipEvent.isCancelled()) {
                        e.setCancelled(true);
                        player.updateInventory();
                    }
                }
            }
        }
    }

    /**
     *
     * @param event {@link InventoryDragEvent}
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void inventoryDrag(final InventoryDragEvent event) {
        final ArmorType type = ArmorType.matchType(event.getOldCursor());
        if (event.getRawSlots().isEmpty()) {
            return;
        }
        if (type != null && type.getSlot() == event.getRawSlots().stream().findFirst().orElse(0)) {
            final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(
                    (Player) event.getWhoClicked(),
                    EquipMethod.DRAG,
                    type,
                    null,
                    event.getOldCursor());

            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                event.setResult(Event.Result.DENY);
                event.setCancelled(true);
            }
        }
    }

    /**
     *
     * @param e {@link PlayerItemBreakEvent}
     */
    @SuppressWarnings("deprecation")
    @EventHandler
    public void itemBreakEvent(final PlayerItemBreakEvent e) {
        final ArmorType type = ArmorType.matchType(e.getBrokenItem());
        if (type == null) {
            return;
        }
        final Player p = e.getPlayer();
        final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(p, EquipMethod.BROKE, type, e.getBrokenItem(), null);
        Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
        if (!armorEquipEvent.isCancelled()) {
            return;
        }
        final ItemStack i = e.getBrokenItem().clone();
        i.setAmount(1);
        i.setDurability((short) (i.getDurability() - 1));
        if (type.equals(ArmorType.HELMET)) {
            p.getInventory().setHelmet(i);
        } else if (type.equals(ArmorType.CHESTPLATE)) {
            p.getInventory().setChestplate(i);
        } else if (type.equals(ArmorType.LEGGINGS)) {
            p.getInventory().setLeggings(i);
        } else if (type.equals(ArmorType.BOOTS)) {
            p.getInventory().setBoots(i);
        }
    }

    /**
     *
     * @param e {@link PlayerDeathEvent}
     */
    @EventHandler
    public void playerDeathEvent(final PlayerDeathEvent e) {
        final Player p = e.getEntity();

        if (e.getKeepInventory()) {
            return;
        }

        Arrays.stream(p.getInventory().getArmorContents())
                .filter(BukkitItemUtil::isNotNull)
                .forEach(itemStack ->
                        new ArmorEquipEvent(p,
                                EquipMethod.DEATH,
                                ArmorType.matchType(itemStack),
                                itemStack,
                                null)
                );
    }
}