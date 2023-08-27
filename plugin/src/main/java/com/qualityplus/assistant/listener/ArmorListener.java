package com.qualityplus.assistant.listener;

import com.qualityplus.assistant.api.util.BukkitItemUtil;
import com.qualityplus.assistant.base.event.ArmorEquipEvent;
import com.qualityplus.assistant.base.event.ArmorEquipEvent.EquipMethod;
import com.qualityplus.assistant.listener.armorhandler.ArmorClickHandler;
import com.qualityplus.assistant.listener.armorhandler.ArmorShiftClickHandler;
import com.qualityplus.assistant.util.armor.ArmorType;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.Inventory;
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
    private static final ArmorClickHandler SHIFT_CLICK_HANDLER = new ArmorShiftClickHandler();
    private static final ArmorClickHandler NORMAL_CLICK_HANDLER = new ArmorShiftClickHandler();

    /**
     *
     * @param e {@link InventoryClickEvent}
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void inventoryClick(final InventoryClickEvent e) {
        if (e.getAction() == InventoryAction.NOTHING) {
            return;
        }

        final boolean shift = e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT);

        final Inventory clickedInventory = e.getClickedInventory();

        final SlotType slotType = e.getSlotType();

        if (slotType != SlotType.ARMOR
                && slotType != SlotType.QUICKBAR
                && slotType != SlotType.CONTAINER) {
            return;
        }

        if (clickedInventory != null && !clickedInventory.getType().equals(InventoryType.PLAYER)) {
            return;
        }

        final InventoryType invType = e.getInventory().getType();

        if (inventoryIsNotCraftingOrPlayer(invType)) {
            return;
        }
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        final ArmorType newArmorType = ArmorType.matchType(shift ? e.getCurrentItem() : e.getCursor());

        if (!shift && newArmorType != null && e.getRawSlot() != newArmorType.getSlot()) {
            return;
        }

        if (shift) {
            SHIFT_CLICK_HANDLER.handle(newArmorType, e);
        } else {
            NORMAL_CLICK_HANDLER.handle(newArmorType, e);
        }
    }

    private boolean inventoryIsNotCraftingOrPlayer(final InventoryType invType) {
        return !invType.equals(InventoryType.CRAFTING) && !invType.equals(InventoryType.PLAYER);
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

        final Action action = e.getAction();

        if (action == Action.PHYSICAL) {
            return;
        }

        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        final Block block = e.getClickedBlock();
        final Player player = e.getPlayer();

        if (block != null && action == Action.RIGHT_CLICK_BLOCK && !player.isSneaking()) {
            final Material mat = e.getClickedBlock().getType();

            for (String blocked : BLOCKED_MATERIALS) {
                if (mat.name().equalsIgnoreCase(blocked) || mat.name().contains(blocked.toUpperCase())) {
                    return;
                }
            }
        }

        final ArmorType newArmorType = ArmorType.matchType(e.getItem());

        if (newArmorType == null) {
            return;
        }

        final PlayerInventory inv = e.getPlayer().getInventory();

        final boolean doesntHaveArmor = playerDoesntHaveArmor(newArmorType, inv);

        if (!doesntHaveArmor) {
            return;
        }

        final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(e.getPlayer(), EquipMethod.HOTBAR,
                newArmorType, null, e.getItem());

        Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);

        if (!armorEquipEvent.isCancelled()) {
            return;
        }

        e.setCancelled(true);
        player.updateInventory();
    }

    private boolean playerDoesntHaveArmor(final ArmorType type, final PlayerInventory inv) {
        final ItemStack itemStack = type.getInventoryFunction().apply(inv);

        return BukkitItemUtil.isNull(itemStack);
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
                                null));
    }
}
