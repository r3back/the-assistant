package com.qualityplus.assistant.inventory;

import com.qualityplus.assistant.api.gui.ClickableInventory;
import com.qualityplus.assistant.api.gui.LoreWrapper;
import com.qualityplus.assistant.api.util.IPlaceholder;
import com.qualityplus.assistant.inventory.background.Background;
import com.qualityplus.assistant.util.StringUtils;
import com.qualityplus.assistant.util.inventory.InventoryUtils;
import com.qualityplus.assistant.util.itemstack.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class GUI implements InventoryHolder, ClickableInventory<Item, Background> {
    protected Inventory inventory;
    protected int page = 1;
    protected boolean hasNext;
    protected int maxPerPage;

    public GUI(final int size, final String title) {
        this.inventory = Bukkit.createInventory(this, size, StringUtils.color(title));
    }

    public GUI(final SimpleGUI simpleGUI) {
        this.inventory = Bukkit.createInventory(this, simpleGUI.getSize(), StringUtils.color(simpleGUI.getTitle()));
    }

    protected void fillInventory(final SimpleGUI simpleGUI) {
        InventoryUtils.fillInventory(inventory, simpleGUI.getBackground());
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    @Override
    public boolean isClickingDecoration(final Integer clickedSlot, final Background background) {
        return Optional.ofNullable(background.items)
                .map(back -> back.keySet().stream().anyMatch(slot -> slot.equals(clickedSlot)))
                .orElse(false);
    }

    protected void handleItemCommandClick(final Player player, final Item item) {
        player.closeInventory();

        Optional.ofNullable(item)
                .map(i -> i.command)
                .filter(Objects::nonNull)
                .ifPresent(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName())));
    }

    @Override
    public void setItem(final Item item) {

        if (!item.enabled || item.slot == null) {
            return;
        }

        inventory.setItem(item.slot, ItemStackUtils.makeItem(item));
    }

    @Override
    public void setItem(final Item item, final List<IPlaceholder> placeholderList) {
        if (!item.enabled || item.slot == null) {
            return;
        }

        inventory.setItem(item.slot, ItemStackUtils.makeItem(item, placeholderList));
    }

    @Override
    public void setItem(final Item item, final LoreWrapper loreWrapper) {
        if (!item.enabled || item.slot == null) {
            return;
        }

        inventory.setItem(item.slot, ItemStackUtils.makeItem(item, loreWrapper));
    }

    @Override
    public void setItem(final Item item, final List<IPlaceholder> placeholderList,
                        final LoreWrapper loreWrapper) {
        if (!item.enabled || item.slot == null) {
            return;
        }

        inventory.setItem(item.slot, ItemStackUtils.makeItem(item, placeholderList, loreWrapper));
    }

    protected boolean isItem(final int slot, final Item item) {
        return item != null && item.enabled && item.slot == slot;
    }

    public void onInventoryClose(final InventoryCloseEvent event) {

    }

    public void onInventoryOpen(final InventoryOpenEvent event) {

    }

    public void onInventoryDrag(final InventoryDragEvent event) {

    }

    public static ClickTarget getTarget(final InventoryClickEvent e) {
        return e.getClickedInventory() == null ?
                ClickTarget.NONE :
                e.getClickedInventory().equals(e.getInventory()) ? ClickTarget.INSIDE : ClickTarget.PLAYER;
    }

    protected enum ClickTarget {
        NONE,
        /**
         * CALLED WHEN PLAYER CLICKS INSIDE PLUGIN GUI
         */
        INSIDE,
        /**
         * CALLED WHEN PLAYER CLICKS IN IT'S OWN INVENTORY
         */
        PLAYER
    }
}
