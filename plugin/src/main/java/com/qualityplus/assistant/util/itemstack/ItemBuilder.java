package com.qualityplus.assistant.util.itemstack;

import com.cryptomorin.xseries.XMaterial;
import com.qualityplus.assistant.inventory.Item;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Item Builder
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ItemBuilder {
    private final Item item = new Item();

    /**
     *
     * @return {@link ItemBuilder}
     */
    public static ItemBuilder of() {
        return new ItemBuilder();
    }

    /**
     *
     * @param material {@link XMaterial}
     * @param amount   item amount
     * @param title    item title
     * @param lore     item lore
     * @return {@link ItemBuilder}
     */
    public static ItemBuilder of(final XMaterial material, final int amount, final String title,
                                 final List<String> lore) {
        return new ItemBuilder()
                .material(material)
                .amount(amount)
                .title(title)
                .lore(lore);
    }

    /**
     *
     * @param material {@link XMaterial}
     * @param slot     item slot
     * @param amount   item amount
     * @param title    item title
     * @param lore     item lore
     * @return {@link ItemBuilder}
     */
    public static ItemBuilder of(final XMaterial material, final int slot, final int amount,
                                 final String title, final List<String> lore) {
        return of(material, amount, title, lore)
                .slot(slot)
                .enabled(true);
    }

    /**
     *
     * @param material {@link XMaterial}
     * @param amount   item amount
     * @return {@link ItemBuilder}
     */
    public static ItemBuilder of(final XMaterial material, final int amount) {
        return of()
                .material(material)
                .amount(amount)
                .enabled(true);
    }

    /**
     *
     * @param item {@link Item}
     * @return {@link ItemBuilder}
     */
    public static ItemBuilder of(final Item item) {
        return of()
                .material(item.material)
                .amount(item.amount)
                .displayName(item.displayName)
                .headData(item.headData)
                .headOwner(item.headOwner)
                .headOwnerUUID(item.headOwnerUUID)
                .lore(item.lore)
                .slot(Optional.ofNullable(item.slot).orElse(0))
                .enabled(item.enabled)
                .command(item.command)
                .enchanted(item.enchanted);
    }

    /**
     *
     * @param material material
     * @return {@link ItemBuilder}
     */
    public ItemBuilder material(final XMaterial material) {
        this.item.material = material;
        return this;
    }

    /**
     *
     * @param displayName displayName
     * @return {@link ItemBuilder}
     */
    public ItemBuilder displayName(final String displayName) {
        this.item.displayName = displayName;
        return this;
    }

    /**
     *
     * @param headOwnerUUID headOwnerUUID
     * @return {@link ItemBuilder}
     */
    public ItemBuilder headOwnerUUID(final UUID headOwnerUUID) {
        this.item.headOwnerUUID = headOwnerUUID;
        return this;
    }

    /**
     *
     * @param slot slot
     * @return {@link ItemBuilder}
     */
    public ItemBuilder slot(final int slot) {
        this.item.slot = slot;
        return this;
    }

    /**
     *
     * @param amount amount
     * @return {@link ItemBuilder}
     */
    public ItemBuilder amount(final int amount) {
        this.item.amount = amount;
        return this;
    }

    /**
     *
     * @param lore lore
     * @return {@link ItemBuilder}
     */
    public ItemBuilder lore(final List<String> lore) {
        this.item.lore = lore;
        return this;
    }

    /**
     *
     * @param lore lore
     * @return {@link ItemBuilder}
     */
    public ItemBuilder lore(final String... lore) {
        this.item.lore = Arrays.stream(lore).collect(Collectors.toList());
        return this;
    }

    /**
     *
     * @param title title
     * @return {@link ItemBuilder}
     */
    public ItemBuilder title(final String title) {
        this.item.displayName = title;
        return this;
    }

    /**
     *
     * @param command command
     * @return {@link ItemBuilder}
     */
    public ItemBuilder command(final String command) {
        this.item.command = command;
        return this;
    }

    /**
     *
     * @param enabled enabled
     * @return {@link ItemBuilder}
     */
    public ItemBuilder enabled(final boolean enabled) {
        this.item.enabled = enabled;
        return this;
    }

    /**
     *
     * @param headData headData
     * @return {@link ItemBuilder}
     */
    public ItemBuilder headData(final String headData) {
        this.item.headData = headData;
        return this;
    }

    /**
     *
     * @param headOwner headOwner
     * @return {@link ItemBuilder}
     */
    public ItemBuilder headOwner(final String headOwner) {
        this.item.headOwner = headOwner;
        return this;
    }

    /**
     *
     * @param enchanted enchanted
     * @return {@link ItemBuilder}
     */
    public ItemBuilder enchanted(final boolean enchanted) {
        this.item.enchanted = enchanted;
        return this;
    }

    /**
     *
     * @return {@link Item}
     */
    public Item build() {
        return this.item;
    }

    /**
     *
     * @return {@link ItemStack}
     */
    public ItemStack buildStack() {
        return ItemStackUtils.makeItem(build());
    }
}