package com.qualityplus.assistant.api.gui.fake;

import com.qualityplus.assistant.api.gui.FakeInventory;
import com.qualityplus.assistant.api.util.BukkitItemUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Fake Inventory Implementation
 */
@Getter
@RequiredArgsConstructor
public final class FakeInventoryImpl implements FakeInventory {
    private InventoryView inventoryView;
    private Inventory inventory;
    private final int slots;

    /**
     * Constructor with inventory view and slots
     *
     * @param inventoryView {@link InventoryView}
     * @param slots         max slots amount
     */
    public FakeInventoryImpl(final InventoryView inventoryView, final int slots) {
        this.inventoryView = inventoryView;
        this.slots = slots;
    }

    /**
     * Constructor with inventory and slots
     *
     * @param inventory {@link Inventory}
     * @param slots     max slots amount
     */
    public FakeInventoryImpl(final Inventory inventory, final int slots) {
        this.inventory = inventory;
        this.slots = slots;
    }

    @Override
    public void removeItems(final ItemStack toRemove, final int paramAmount) {
        int amount = paramAmount;
        for (int i = this.slots; i >= 0; i--) {
            if (amount <= 0) {
                break;
            }

            final ItemStack itemStack = this.inventory.getItem(i);

            if (itemStack == null || itemStack.getType() == Material.AIR || !itemStack.isSimilar(toRemove)) {
                continue;
            }

            final int toRemoveAmount = Math.min(itemStack.getAmount(), amount);

            final ItemStack newItemStack = BukkitItemUtil.getItemWithout(itemStack, toRemoveAmount);

            this.inventory.setItem(i, newItemStack);

            amount -= toRemoveAmount;
        }
    }

    @Override
    public Map<ItemStack, Integer> getItemsWithAmount() {
        final Map<ItemStack, Integer> itemStackMap = new HashMap<>();

        for (int i = 0; i < this.slots; i++) {
            final ItemStack itemStack = this.inventory.getItem(i);

            if (itemStack == null || itemStack.getType() == Material.AIR) {
                continue;
            }

            final ItemStack clone = BukkitItemUtil.getItemWith(itemStack.clone(), 1);

            final Integer amount = itemStackMap.getOrDefault(clone, 0);

            itemStackMap.put(clone, itemStack.getAmount() + amount);
        }

        return itemStackMap;
    }

    @Override
    public void setItems(final Map<Integer, ItemStack> items) {
        for (int i = 0; i <= items.size(); i++) {
            this.inventory.setItem(i, items.get(i));
        }
    }

    @Override
    public Map<Integer, ItemStack> getItems() {
        final Map<Integer, ItemStack> itemStackMap = new HashMap<>();

        for (int i = 0; i < this.slots; i++) {
            final ItemStack itemStack = this.inventory.getItem(i);

            if (itemStack == null || itemStack.getType() == Material.AIR) {
                continue;
            }

            itemStackMap.put(i, itemStack);
        }

        return itemStackMap;
    }

    @Override
    public ItemStack[] getItemsArray() {
        final List<ItemStack> items = new ArrayList<>(getItems().values());

        return BukkitItemUtil.fromList(items);
    }

    @Override
    public ItemStack removeOneFromLastItem() {
        final Map<Integer, ItemStack> itemStackMap = new HashMap<>();

        Integer max = null;

        for (int i = 0; i < this.slots; i++) {
            final ItemStack itemStack = this.inventory.getItem(i);

            if (BukkitItemUtil.isNull(itemStack)) {
                continue;
            }

            max = i;

            itemStackMap.put(i, itemStack);
        }

        if (max != null) {
            ItemStack lastItem = itemStackMap.getOrDefault(max, null);

            if (BukkitItemUtil.isNull(lastItem)) {
                return null;
            }

            lastItem = lastItem.clone();

            final ItemStack toPut = BukkitItemUtil.getItemWithout(lastItem, 1);

            this.inventory.setItem(max, toPut);

            return BukkitItemUtil.getItemWith(lastItem, 1);
        }

        return null;
    }

    @Override
    public int getEmptySlots() {
        int emptySlots = 0;

        for (int i = this.slots; i >= 0; i--) {
            final ItemStack itemStack = this.inventory.getItem(i);

            if (itemStack == null || itemStack.getType() == Material.AIR) {
                emptySlots++;
            }
        }

        return emptySlots;
    }

    @Override
    public void removeItems() {
        for (int i = 0; i < this.slots; i++) {
            final ItemStack itemStack = this.inventory.getItem(i);

            if (itemStack == null || itemStack.getType() == Material.AIR) {
                continue;
            }

            this.inventory.setItem(i, null);
        }

    }

    @Override
    public void remove() {
        Optional.ofNullable(this.inventory)
                //.map(InventoryView::getPlayer)
                .filter(Objects::nonNull)
                .ifPresent(Inventory::clear);
                //.ifPresent(HumanEntity::remove);
    }

    @Override
    public int getEntityId() {
        return 0;
    }
}
