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

/**
 * GUI representation
 */
public abstract class GUI implements InventoryHolder, ClickableInventory<Item, Background> {
    protected Inventory inventory;
    protected int page = 1;
    protected boolean hasNext;
    protected int maxPerPage;

    /**
     * No parameters constructor
     *
     */
    public GUI() {
    }

    /**
     * Constructor with gui tile and size
     *
     * @param size  inventory size
     * @param title inventory title
     */
    public GUI(final int size, final String title) {
        this.inventory = Bukkit.createInventory(this, size, StringUtils.color(title));
    }

    /**
     *
     * @param simpleGUI {@link SimpleGUI}
     */
    public GUI(final SimpleGUI simpleGUI) {
        this.inventory = Bukkit.createInventory(this, simpleGUI.getSize(), StringUtils.color(simpleGUI.getTitle()));
    }

    @Override
    public boolean isClickingDecoration(final Integer clickedSlot, final Background background) {
        return Optional.ofNullable(background.getItems())
                .map(back -> back.keySet().stream().anyMatch(slot -> slot.equals(clickedSlot)))
                .orElse(false);
    }

    @Override
    public void setItem(final Item item) {

        if (!item.isEnabled() || item.getSlot() == null) {
            return;
        }

        this.inventory.setItem(item.getSlot(), ItemStackUtils.makeItem(item));
    }

    @Override
    public void setItem(final Item item, final List<IPlaceholder> placeholderList) {
        if (!item.isEnabled() || item.getSlot() == null) {
            return;
        }

        this.inventory.setItem(item.getSlot(), ItemStackUtils.makeItem(item, placeholderList));
    }

    @Override
    public void setItem(final Item item, final LoreWrapper loreWrapper) {
        if (!item.isEnabled() || item.getSlot() == null) {
            return;
        }

        this.inventory.setItem(item.getSlot(), ItemStackUtils.makeItem(item, loreWrapper));
    }

    @Override
    public void setItem(final Item item, final List<IPlaceholder> placeholderList,
                        final LoreWrapper loreWrapper) {
        if (!item.isEnabled() || item.getSlot() == null) {
            return;
        }

        this.inventory.setItem(item.getSlot(), ItemStackUtils.makeItem(item, placeholderList, loreWrapper));
    }

    /**
     *
     * @return {@link Inventory}
     */
    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Fill inventory with gui background
     *
     * @param simpleGUI {@link SimpleGUI}
     */
    protected void fillInventory(final SimpleGUI simpleGUI) {
        InventoryUtils.fillInventory(this.inventory, simpleGUI.getBackground());
    }

    /**
     *
     * @param slot slot
     * @param item {@link Item}
     * @return true if it's item
     */
    protected boolean isItem(final int slot, final Item item) {
        return item != null && item.isEnabled() && item.getSlot() == slot;
    }

    /**
     *
     * @param event {@link InventoryCloseEvent}
     */
    public void onInventoryClose(final InventoryCloseEvent event) {

    }

    /**
     *
     * @param event {@link InventoryOpenEvent}
     */
    public void onInventoryOpen(final InventoryOpenEvent event) {

    }

    /**
     *
     * @param event {@link InventoryDragEvent}
     */
    public void onInventoryDrag(final InventoryDragEvent event) {

    }

    /**
     * Handle when a player clicks an item with command
     *
     * @param player {@link Player}
     * @param item   {@link Item}
     */
    protected void handleItemCommandClick(final Player player, final Item item) {
        player.closeInventory();

        Optional.ofNullable(item)
                .map(Item::getCommand)
                .filter(Objects::nonNull)
                .ifPresent(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName())));
    }

    /**
     * Retrieves click target
     * @param e {@link InventoryClickEvent}
     * @return {@link ClickTarget}
     */
    public static ClickTarget getTarget(final InventoryClickEvent e) {
        return e.getClickedInventory() == null ?
                ClickTarget.NONE :
                e.getClickedInventory().equals(e.getInventory()) ? ClickTarget.INSIDE : ClickTarget.PLAYER;
    }

    /**
     * Represents Inventory click targets
     */
    public static enum ClickTarget {
        /**
         * Called when none of targets is clicked
         */
        NONE,
        /**
         * Called when a player clicks inside plugin gui
         */
        INSIDE,
        /**
         * Called when a player clicks inside its own inventory
         */
        PLAYER
    }
}
