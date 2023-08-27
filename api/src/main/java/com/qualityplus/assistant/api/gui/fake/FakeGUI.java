package com.qualityplus.assistant.api.gui.fake;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Fake GUI
 */
public final class FakeGUI implements InventoryHolder {
    public static final String FAKE_INVENTORY_NAME = "Fake";
    private final Inventory inventory;
    private final int slots;

    /**
     * Constructor with max slots amount
     *
     * @param slots max slots amount
     */
    public FakeGUI(final int slots) {
        this.inventory = Bukkit.createInventory(this, 54, FAKE_INVENTORY_NAME);
        this.slots = slots;
    }

    /**
     * Constructor with two arguments
     *
     * @param contents Array of {@link ItemStack}
     * @param maxSlots Inventory max slots
     */
    public FakeGUI(final ItemStack[] contents, final int maxSlots) {
        this.inventory = Bukkit.createInventory(this, 54, FAKE_INVENTORY_NAME);
        this.inventory.setContents(contents);
        this.slots = maxSlots;
    }

    @Override
    public @NotNull Inventory getInventory() {
        for (int i = this.slots; i < this.inventory.getSize(); i++) {
            this.inventory.setItem(i, XMaterial.BARRIER.parseItem());
        }

        return this.inventory;
    }
}
