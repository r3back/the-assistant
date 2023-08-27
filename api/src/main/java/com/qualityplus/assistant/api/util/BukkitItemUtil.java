package com.qualityplus.assistant.api.util;

import com.cryptomorin.xseries.XMaterial;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Utility class to handle Bukkit Items
 */
@UtilityClass
public class BukkitItemUtil {
    /**
     * Drops item in player location
     *
     * @param player    {@link Player}
     * @param itemStack {@link ItemStack}
     */
    public void dropItem(final Player player, final ItemStack itemStack) {
        dropItem(player.getLocation(), itemStack);
    }

    /**
     * Drop item in specific location
     * @param location  {@link Location}
     * @param itemStack {@link ItemStack}
     */
    public void dropItem(final Location location, final ItemStack itemStack) {
        if (location == null) {
            return;
        }

        Optional.ofNullable(location.getWorld())
                .ifPresent(w -> w.dropItem(location, itemStack));
    }

    /**
     * Retrieves clone of item
     *
     * @param itemStack {@link ItemStack}
     * @return {@link ItemStack}
     */
    public ItemStack cloneOrNull(final ItemStack itemStack) {
        return Optional.ofNullable(itemStack)
                .map(ItemStack::clone)
                .orElse(null);
    }

    /**
     * Retrieves serialized item
     *
     * @param itemStack {@link ItemStack}
     * @return item serialized
     */
    public String serialize(final ItemStack itemStack) {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(
                    byteArrayOutputStream
            );
            bukkitObjectOutputStream.writeObject(itemStack);
            bukkitObjectOutputStream.flush();

            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * Deserialize a item string
     *
     * @param string item string to be deserialized
     * @return {@link ItemStack}
     */
    public ItemStack deserialize(final String string) {
        try {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    Base64.getDecoder().decode(string)
            );
            final BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(byteArrayInputStream);
            return (ItemStack) bukkitObjectInputStream.readObject();
        } catch (final IOException | ClassNotFoundException exception) {
            return XMaterial.AIR.parseItem();
        }
    }

    /**
     * Return item with a specified reduced amount
     *
     * @param itemStack      {@link ItemStack}
     * @param amountToRemove amount to reduce from item
     * @return {@link ItemStack}
     */
    public ItemStack getItemWithout(final @Nullable ItemStack itemStack, final int amountToRemove) {
        if (itemStack == null || itemStack.getAmount() - amountToRemove <= 0) {
            return null;
        }

        itemStack.setAmount(itemStack.getAmount() - amountToRemove);

        return itemStack;
    }

    /**
     * Return item with a specified added amount
     *
     * @param itemStack      {@link ItemStack}
     * @param amountToAdd    amount to add to item
     * @return {@link ItemStack}
     */
    public ItemStack getItemWithAdd(final ItemStack itemStack, final int amountToAdd) {
        itemStack.setAmount(Math.min(itemStack.getMaxStackSize(), itemStack.getAmount() + amountToAdd));

        return itemStack;
    }

    /**
     * Return item with a specified quantity amount
     *
     * @param itemStack      {@link ItemStack}
     * @param finalAmount    new item amount
     * @return {@link ItemStack}
     */
    public ItemStack getItemWith(final ItemStack itemStack, final int finalAmount) {
        itemStack.setAmount(Math.min(itemStack.getMaxStackSize(), finalAmount));

        return itemStack;
    }

    /**
     * Return if item is null or air
     *
     * @param itemStack {@link ItemStack}
     * @return true if item is null or air
     */
    public boolean isNull(final ItemStack itemStack) {
        return (itemStack == null || itemStack.getType() == Material.AIR);
    }

    /**
     * Return if item is not null or air
     *
     * @param itemStack {@link ItemStack}
     * @return true if item is not null or air
     */
    public boolean isNotNull(final ItemStack itemStack) {
        return !isNull(itemStack);
    }

    /**
     * Add custom model data to item
     *
     * @param itemStack       {@link ItemStack}
     * @param customModelData new custom model data
     * @return {@link ItemStack}
     */
    public ItemStack addCustomModelData(final ItemStack itemStack, final Integer customModelData) {
        if (customModelData == null || XMaterial.getVersion() < 13) {
            return itemStack;
        }

        try {
            final ItemMeta meta = itemStack.getItemMeta();

            Optional.ofNullable(meta)
                            .ifPresent(m -> m.setCustomModelData(customModelData));

            itemStack.setItemMeta(meta);
        } catch (final NullPointerException ignored) {
        }

        return itemStack;
    }

    /**
     * Set lore to item
     *
     * @param itemStack {@link ItemStack}
     * @param lore      new item lore
     */
    public void setLore(final ItemStack itemStack, final List<String> lore) {
        final ItemMeta meta = itemStack.getItemMeta();

        Optional.ofNullable(meta).ifPresent(m -> m.setLore(lore));

        Optional.ofNullable(meta).ifPresent(itemStack::setItemMeta);

    }

    /**
     * Retrieves item display name
     *
     * @param itemStack {@link ItemStack}
     * @return item display name
     */
    public String getName(final ItemStack itemStack) {
        final String displayName = Optional.ofNullable(itemStack)
                .map(ItemStack::getItemMeta)
                .map(ItemMeta::getDisplayName)
                .filter(name -> name != null && !name.isEmpty())
                .orElse(null);

        return Optional.ofNullable(displayName)
                .orElse(getMaterialName(itemStack));
    }

    /**
     * Return material item name
     *
     * @param itemStack {@link ItemStack}
     * @return material item name
     */
    public String getMaterialName(final ItemStack itemStack) {
        if (isNull(itemStack)) {
            return "";
        }

        return WordUtils.capitalize(Optional.ofNullable(itemStack)
                .map(ItemStack::getType)
                .map(Material::toString)
                .orElse("")
                .replace("_", " ")
                .toLowerCase());
    }

    /**
     * Retrieves item lore
     *
     * @param item {@link ItemStack}
     * @return item lore
     */
    public List<String> getItemLore(final ItemStack item) {
        if (isNull(item)) {
            return Collections.emptyList();
        }

        return Optional.ofNullable(item)
                .map(ItemStack::getItemMeta)
                .filter(Objects::nonNull)
                .map(ItemMeta::getLore)
                .filter(Objects::nonNull)
                .orElse(new ArrayList<>());
    }

    /**
     * Converts list of items to an array
     *
     * @param itemStacks List of {@link ItemStack}
     * @return Array of {@link ItemStack}
     */
    public ItemStack[] fromList(final List<ItemStack> itemStacks) {
        final ItemStack[] toReturnItems = new ItemStack[itemStacks.size()];

        itemStacks.toArray(toReturnItems);

        return toReturnItems;
    }
}
