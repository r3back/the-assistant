package com.qualityplus.assistant.util.itemstack;

import com.cryptomorin.xseries.XMaterial;
import com.qualityplus.assistant.api.gui.LoreWrapper;
import com.qualityplus.assistant.api.util.BukkitItemUtil;
import com.qualityplus.assistant.api.util.IPlaceholder;
import com.qualityplus.assistant.inventory.Item;
import com.qualityplus.assistant.util.StringUtils;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import org.apache.commons.lang.WordUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.stream.Collectors;

public final class ItemStackUtils {
    private static final LoreWrapper LORE_WRAPPER = new LoreWrapper(60, "&7");

    private ItemStackUtils() {
    }


    public static ItemStack makeItem(final ItemStack item, final int amount, final String name,
                                     final List<String> lore, final boolean hideEnchants) {
        if (item == null) {
            return null;
        }
        item.setAmount(amount);
        final ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if (hideEnchants) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if (lore != null && lore.size() > 0) {
            meta.setLore(StringUtils.color(lore));
        }
        meta.setDisplayName(StringUtils.color(name == null ? " " : name));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack makeItem(final XMaterial material, final int amount,
                                     final String name, final List<String> lore) {
        return makeItem(material.parseItem(), amount, name, lore, true);
    }

    public static ItemStack makeItem(final Item item) {
        return makeItem(item, LORE_WRAPPER);
    }

    public static ItemStack makeItem(final Item item, final List<IPlaceholder> placeholders) {
        return makeItem(item, placeholders, LORE_WRAPPER);
    }

    public static ItemStack makeItem(final Item item, final ItemStack itemStack) {
        return makeItem(item, Collections.emptyList(), LORE_WRAPPER, itemStack);
    }

    public static ItemStack makeItem(final Item item, final List<IPlaceholder> placeholders,
                                     final ItemStack itemStack) {
        return makeItem(item, placeholders, LORE_WRAPPER, itemStack);
    }

    public static ItemStack makeItem(final Item item, final List<IPlaceholder> placeholders,
                                     final ItemStack itemStack, final boolean useItemStackAmount,
                                     boolean hideEnchantments) {
        return makeItem(item, placeholders, LORE_WRAPPER, itemStack, useItemStackAmount, hideEnchantments);
    }

    public static ItemStack makeItem(final Item item, final List<IPlaceholder> placeholders,
                                     final ItemStack itemStack, final boolean useItemStackAmount) {
        return makeItem(item, placeholders, LORE_WRAPPER, itemStack, useItemStackAmount);
    }

    public static ItemStack showEnchantments(final ItemStack itemStack, final boolean show){
        final ItemMeta meta = itemStack.getItemMeta();

        if (show) {
            Optional.ofNullable(meta).ifPresent(m -> m.removeItemFlags(ItemFlag.HIDE_ENCHANTS));
        } else {
            Optional.ofNullable(meta).ifPresent(m -> m.addItemFlags(ItemFlag.HIDE_ENCHANTS));
        }

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack makeItem(final Item item, final LoreWrapper lineWrapper) {
        try {
            final ItemStack itemstack = makeItem(item.material, item.amount, item.displayName, item.lore);

            return getFinalItem(item, itemstack, Collections.emptyList(), lineWrapper);
        } catch (final Exception e) {
            e.printStackTrace();
            return makeItem(XMaterial.STONE, item.amount, item.displayName, item.lore);
        }
    }

    public static ItemStack makeItem(final Item item, final List<IPlaceholder> placeholders,
                                     final LoreWrapper lineWrapper) {
        try {
            final ItemStack itemstack = makeItem(item.material, item.amount, StringUtils.processMulti(item.displayName, placeholders), StringUtils.processMulti(item.lore, placeholders));

            return getFinalItem(item, itemstack, placeholders, lineWrapper);
        } catch (final Exception e) {
            e.printStackTrace();
            return makeItem(XMaterial.STONE, item.amount, StringUtils.processMulti(item.displayName, placeholders), StringUtils.processMulti(item.lore, placeholders));
        }
    }

    public static ItemStack makeItem(final Item item, final List<IPlaceholder> placeholders,
                                     final LoreWrapper lineWrapper, final ItemStack itemStack) {
        try {
            final ItemStack itemstack = makeItem(itemStack.clone(), itemStack.getAmount(), StringUtils.processMulti(item.displayName, placeholders), StringUtils.processMulti(item.lore, placeholders), true);

            return getFinalItem(item, itemstack, placeholders, lineWrapper);
        } catch (final Exception e) {
            e.printStackTrace();
            return makeItem(XMaterial.STONE, item.amount, StringUtils.processMulti(item.displayName, placeholders), StringUtils.processMulti(item.lore, placeholders));
        }
    }

    public static ItemStack makeItem(final Item item, final List<IPlaceholder> placeholders,
                                     final LoreWrapper lineWrapper, final ItemStack itemStack,
                                     final boolean useItemStackAmount) {
        try {
            final int amount = useItemStackAmount ? itemStack.getAmount() : item.amount;

            final ItemStack itemstack = makeItem(itemStack.clone(), amount, StringUtils.processMulti(item.displayName, placeholders), StringUtils.processMulti(item.lore, placeholders), true);

            return getFinalItem(item, itemstack, placeholders, lineWrapper);
        } catch (final Exception e) {
            e.printStackTrace();
            return makeItem(XMaterial.STONE, item.amount, StringUtils.processMulti(item.displayName, placeholders), StringUtils.processMulti(item.lore, placeholders));
        }
    }

    public static ItemStack makeItem(final Item item, final List<IPlaceholder> placeholders,
                                     final LoreWrapper lineWrapper, final ItemStack itemStack,
                                     final boolean useItemStackAmount, final boolean hideEnchants) {
        try {
            final int amount = useItemStackAmount ? itemStack.getAmount() : item.amount;

            final ItemStack itemstack = makeItem(itemStack.clone(), amount, StringUtils.processMulti(item.displayName, placeholders), StringUtils.processMulti(item.lore, placeholders), hideEnchants);

            return getFinalItem(item, itemstack, placeholders, lineWrapper);
        } catch (final Exception e) {
            e.printStackTrace();
            return makeItem(XMaterial.STONE, item.amount, StringUtils.processMulti(item.displayName, placeholders), StringUtils.processMulti(item.lore, placeholders));
        }
    }

    public static ItemStack getFinalItem(final Item item, final ItemStack itemstack,
                                         final List<IPlaceholder> placeholders){
        return getFinalItem(item, itemstack, placeholders, LORE_WRAPPER);
    }


    public static ItemStack getFinalItem(final Item item, final ItemStack itemstack,
                                         final List<IPlaceholder> placeholders,
                                         final LoreWrapper lineWrapper){

        parseWrappedLore(itemstack, lineWrapper);

        parseEnchantment(itemstack, item);

        if (item.material == XMaterial.PLAYER_HEAD && item.headOwner != null) {
            SkullMeta m = (SkullMeta) itemstack.getItemMeta();
            m.setOwner(StringUtils.processMulti(item.headOwner, placeholders));
            itemstack.setItemMeta(m);
        }

        if (item.material == XMaterial.PLAYER_HEAD && item.headData != null) {
            return parseTexture(itemstack, item);
        }
        return BukkitItemUtil.addCustomModelData(itemstack, item.customModelData);

    }

    private static ItemStack parseTexture(final ItemStack itemStack, final Item item) {
        try {
            final NBTItem nbtItem = new NBTItem(itemStack);
            final NBTCompound skull = nbtItem.addCompound("SkullOwner");
            skull.setString("Name", "tr7zw");
            skull.setString("Id", UUID.randomUUID().toString());
            final NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
            texture.setString("Value", item.headData);
            return nbtItem.getItem();
        } catch (final Exception e) {
            e.printStackTrace();
            return itemStack;
        }

    }

    private static void parseEnchantment(final ItemStack itemStack, final Item item) {
        if (!item.enchanted) {
            return;
        }

        final ItemMeta meta = itemStack.getItemMeta();

        meta.addEnchant(Enchantment.KNOCKBACK, 1, false);

        itemStack.setItemMeta(meta);

    }

    public static void parseWrappedLore(final ItemStack itemStack, final LoreWrapper lineWrapper) {
        final List<String> lore = BukkitItemUtil.getItemLore(itemStack);

        final List<String> wrappedLore = new ArrayList<>();

        for (final String line : lore) {
            final String indent = StringUtils.repeat(" ", line.length() - line.trim().length());

            final List<String> wrapped = Arrays.stream(WordUtils.wrap(
                    line.trim(),
                    lineWrapper.getWrapLength(),
                    "\n" + lineWrapper.wrapStart, false
            ).split("\n")).collect(Collectors.toList());


            wrapped.replaceAll(x -> indent + x);

            wrapped.forEach(loreLine -> wrappedLore.add(StringUtils.color(loreLine)));
        }

        BukkitItemUtil.setLore(itemStack, wrappedLore);
    }




}
