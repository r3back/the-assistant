package com.qualityplus.assistant.util.itemstack;

import com.cryptomorin.xseries.XMaterial;
import com.qualityplus.assistant.TheAssistantPlugin;
import com.qualityplus.assistant.api.gui.LoreWrapper;
import com.qualityplus.assistant.api.util.BukkitItemUtil;
import com.qualityplus.assistant.api.util.IPlaceholder;
import com.qualityplus.assistant.inventory.Item;
import com.qualityplus.assistant.util.StringUtils;
import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.WordUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Utility class for ItemStacks
 */
@UtilityClass
public final class ItemStackUtils {
    private static final LoreWrapper LORE_WRAPPER = new LoreWrapper(60, "&7");

    /**
     *
     * @param item         {@link ItemStack}
     * @param amount       item amount
     * @param name         item name
     * @param lore         item lore
     * @param hideEnchants hide enchants
     * @return {@link ItemStack}
     */
    public ItemStack makeItem(final ItemStack item, final int amount, final String name,
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

    /**
     *
     * @param material {@link XMaterial}
     * @param amount   item amount
     * @param name     item name
     * @param lore     item lore
     * @return {@link ItemStack}
     */
    public ItemStack makeItem(final XMaterial material, final int amount,
                                     final String name, final List<String> lore) {
        return makeItem(material.parseItem(), amount, name, lore, true);
    }

    /**
     *
     * @param item {@link Item}
     * @return {@link ItemStack}
     */
    public ItemStack makeItem(final Item item) {
        return makeItem(item, LORE_WRAPPER);
    }

    /**
     *
     * @param item         {@link Item}
     * @param placeholders List of {@link IPlaceholder}
     * @return {@link ItemStack}
     */
    public ItemStack makeItem(final Item item, final List<IPlaceholder> placeholders) {
        return makeItem(item, placeholders, LORE_WRAPPER);
    }

    /**
     *
     * @param item      {@link Item}
     * @param itemStack {@link ItemStack}
     * @return {@link ItemStack}
     */
    public ItemStack makeItem(final Item item, final ItemStack itemStack) {
        return makeItem(item, Collections.emptyList(), LORE_WRAPPER, itemStack);
    }

    /**
     *
     * @param item         {@link Item}
     * @param itemStack    {@link ItemStack}
     * @param placeholders List of {@link IPlaceholder}
     * @return {@link ItemStack}
     */
    public ItemStack makeItem(final Item item, final List<IPlaceholder> placeholders,
                                     final ItemStack itemStack) {
        return makeItem(item, placeholders, LORE_WRAPPER, itemStack);
    }

    /**
     *
     * @param item               {@link Item}
     * @param itemStack          {@link ItemStack}
     * @param placeholders       List of {@link IPlaceholder}
     * @param useItemStackAmount if the amount of itemStack should be used
     * @param hideEnchantments   if enchantments should be hidden
     * @return {@link ItemStack}
     */
    public ItemStack makeItem(final Item item, final List<IPlaceholder> placeholders,
                                     final ItemStack itemStack, final boolean useItemStackAmount,
                                     final boolean hideEnchantments) {
        return makeItem(item, placeholders, LORE_WRAPPER, itemStack, useItemStackAmount, hideEnchantments);
    }

    /**
     *
     * @param item               {@link Item}
     * @param placeholders       List of {@link IPlaceholder}
     * @param itemStack          {@link ItemStack}
     * @param useItemStackAmount if the amount of itemStack should be used
     * @return {@link ItemStack}
     */
    public ItemStack makeItem(final Item item, final List<IPlaceholder> placeholders,
                                     final ItemStack itemStack, final boolean useItemStackAmount) {
        return makeItem(item, placeholders, LORE_WRAPPER, itemStack, useItemStackAmount);
    }

    /**
     *
     * @param itemStack          {@link ItemStack}
     * @param showEnchantments   if enchantment should be showed
     * @return {@link ItemStack}
     */
    public ItemStack showEnchantments(final ItemStack itemStack, final boolean showEnchantments) {
        final ItemMeta meta = itemStack.getItemMeta();

        if (showEnchantments) {
            Optional.ofNullable(meta).ifPresent(m -> m.removeItemFlags(ItemFlag.HIDE_ENCHANTS));
        } else {
            Optional.ofNullable(meta).ifPresent(m -> m.addItemFlags(ItemFlag.HIDE_ENCHANTS));
        }

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    /**
     *
     * @param item        {@link Item}
     * @param loreWrapper {@link LoreWrapper}
     * @return {@link ItemStack}
     */
    public ItemStack makeItem(final Item item, final LoreWrapper loreWrapper) {
        try {
            final ItemStack itemstack = makeItem(item.getMaterial(), item.getAmount(), item.getDisplayName(), item.getLore());

            return getFinalItem(item, itemstack, Collections.emptyList(), loreWrapper);
        } catch (final NullPointerException e) {
            e.printStackTrace();
            return makeItem(XMaterial.STONE, item.getAmount(), item.getDisplayName(), item.getLore());
        }
    }

    /**
     *
     * @param item         {@link Item}
     * @param placeholders List of {@link IPlaceholder}
     * @param lineWrapper  {@link LoreWrapper}
     * @return {@link ItemStack}
     */
    public ItemStack makeItem(final Item item, final List<IPlaceholder> placeholders, final LoreWrapper lineWrapper) {
        final String displayName = StringUtils.processMulti(item.getDisplayName(), placeholders);

        final List<String> lore = StringUtils.processMulti(item.getLore(), placeholders);

        try {
            final ItemStack itemstack = makeItem(item.getMaterial(), item.getAmount(), displayName, lore);

            return getFinalItem(item, itemstack, placeholders, lineWrapper);
        } catch (final NullPointerException e) {
            e.printStackTrace();
            return makeItem(XMaterial.STONE, item.getAmount(), displayName, lore);
        }
    }

    /**
     *
     * @param item         {@link Item}
     * @param placeholders List of {@link IPlaceholder}
     * @param lineWrapper  {@link LoreWrapper}
     * @param itemStack    {@link ItemStack}
     * @return {@link ItemStack}
     */
    public ItemStack makeItem(final Item item, final List<IPlaceholder> placeholders, final LoreWrapper lineWrapper,
                              final ItemStack itemStack) {
        final String displayName = StringUtils.processMulti(item.getDisplayName(), placeholders);

        final List<String> lore = StringUtils.processMulti(item.getLore(), placeholders);

        try {
            final ItemStack itemstack = makeItem(itemStack.clone(), itemStack.getAmount(), displayName, lore, true);

            return getFinalItem(item, itemstack, placeholders, lineWrapper);
        } catch (final NullPointerException e) {
            e.printStackTrace();
            return makeItem(XMaterial.STONE, item.getAmount(), displayName, lore);
        }
    }

    /**
     *
     * @param item               {@link Item}
     * @param itemStack          {@link ItemStack}
     * @param lineWrapper        {@link LoreWrapper}
     * @param placeholders       List of {@link IPlaceholder}
     * @param useItemStackAmount if the amount of itemStack should be used
     * @return {@link ItemStack}
     */
    public ItemStack makeItem(final Item item, final List<IPlaceholder> placeholders,
                                     final LoreWrapper lineWrapper, final ItemStack itemStack,
                                     final boolean useItemStackAmount) {

        final String displayName = StringUtils.processMulti(item.getDisplayName(), placeholders);

        final List<String> lore = StringUtils.processMulti(item.getLore(), placeholders);

        try {
            final int amount = useItemStackAmount ? itemStack.getAmount() : item.getAmount();

            final ItemStack itemstack = makeItem(itemStack.clone(), amount, displayName, lore, true);

            return getFinalItem(item, itemstack, placeholders, lineWrapper);
        } catch (final NullPointerException e) {
            e.printStackTrace();
            return makeItem(XMaterial.STONE, item.getAmount(), displayName, lore);
        }
    }

    /**
     *
     * @param item               {@link Item}
     * @param loreWrapper        {@link LoreWrapper}
     * @param itemStack          {@link ItemStack}
     * @param placeholders       List of {@link IPlaceholder}
     * @param useItemStackAmount if the amount of itemStack should be used
     * @param hideEnchants       if enchantments should be hidden
     * @return {@link ItemStack}
     */
    public ItemStack makeItem(final Item item, final List<IPlaceholder> placeholders,
                              final LoreWrapper loreWrapper, final ItemStack itemStack,
                              final boolean useItemStackAmount, final boolean hideEnchants) {
        final String displayName = StringUtils.processMulti(item.getDisplayName(), placeholders);

        final List<String> lore = StringUtils.processMulti(item.getLore(), placeholders);

        try {
            final int amount = useItemStackAmount ? itemStack.getAmount() : item.getAmount();

            final ItemStack itemstack = makeItem(itemStack.clone(), amount, displayName, lore, hideEnchants);

            return getFinalItem(item, itemstack, placeholders, loreWrapper);
        } catch (final NullPointerException e) {
            e.printStackTrace();
            return makeItem(XMaterial.STONE, item.getAmount(), displayName, lore);
        }
    }

    /**
     *
     * @param item         {@link Item}
     * @param itemstack    {@link ItemStack}
     * @param placeholders List of {@link IPlaceholder}
     * @return {@link ItemStack}
     */
    public ItemStack getFinalItem(final Item item, final ItemStack itemstack,
                                  final List<IPlaceholder> placeholders) {
        return getFinalItem(item, itemstack, placeholders, LORE_WRAPPER);
    }


    /**
     *
     * @param item         {@link Item}
     * @param itemstack    {@link ItemStack}
     * @param loreWrapper  {@link LoreWrapper}
     * @param placeholders List of {@link IPlaceholder}
     * @return {@link ItemStack}
     */
    public ItemStack getFinalItem(final Item item, final ItemStack itemstack,
                                  final List<IPlaceholder> placeholders,
                                  final LoreWrapper loreWrapper) {

        parseWrappedLore(itemstack, loreWrapper);

        parseEnchantment(itemstack, item);

        final ItemStack itemWithModelData = BukkitItemUtil.addCustomModelData(itemstack, item.getCustomModelData());

        if (item.getMaterial() == XMaterial.PLAYER_HEAD && item.getHeadOwner() != null) {
            final SkullMeta m = (SkullMeta) itemWithModelData.getItemMeta();
            m.setOwner(StringUtils.processMulti(item.getHeadOwner(), placeholders));
            itemWithModelData.setItemMeta(m);
        }

        if (item.getMaterial() == XMaterial.PLAYER_HEAD && item.getHeadData() != null) {
            return parseTexture(itemWithModelData, item);
        }
        return itemWithModelData;

    }


    /**
     * Parses lore with specific wrapper
     *
     * @param itemStack   {@link ItemStack}
     * @param loreWrapper {@link LoreWrapper}
     */
    public void parseWrappedLore(final ItemStack itemStack, final LoreWrapper loreWrapper) {
        final List<String> lore = BukkitItemUtil.getItemLore(itemStack);

        final List<String> wrappedLore = new ArrayList<>();

        for (final String line : lore) {
            final String indent = StringUtils.repeat(" ", line.length() - line.trim().length());

            final List<String> wrapped = Arrays.stream(WordUtils.wrap(
                    line.trim(),
                    loreWrapper.getWrapLength(),
                    "\n" + loreWrapper.getWrapStart(), false
            ).split("\n")).collect(Collectors.toList());


            wrapped.replaceAll(x -> indent + x);

            wrapped.forEach(loreLine -> wrappedLore.add(StringUtils.color(loreLine)));
        }

        BukkitItemUtil.setLore(itemStack, wrappedLore);
    }

    private ItemStack parseTexture(final ItemStack itemStack, final Item item) {
        try {
            final String textureValue = item.getHeadData();
            final UUID uuid = UUID.randomUUID();
            if (TheAssistantPlugin.getAPI().getNms().isNewNBTAPIResolver()) {
                NBT.modifyComponents(itemStack, nbt -> {
                    final ReadWriteNBT profileNbt = nbt.getOrCreateCompound("minecraft:profile");
                    profileNbt.setUUID("id", uuid);
                    final ReadWriteNBT propertiesNbt = profileNbt.getCompoundList("properties").addCompound();
                    propertiesNbt.setString("name", "textures");
                    propertiesNbt.setString("value", textureValue);
                });
            } else {
                NBT.modify(itemStack, nbt -> {
                    final ReadWriteNBT skullOwnerCompound = nbt.getOrCreateCompound("SkullOwner");

                    skullOwnerCompound.setUUID("Id", UUID.randomUUID());

                    skullOwnerCompound.getOrCreateCompound("Properties")
                            .getCompoundList("textures")
                            .addCompound()
                            .setString("Value", textureValue);
                });
            }
            return itemStack;
        } catch (final NullPointerException e) {
            e.printStackTrace();
            return itemStack;
        }
    }

    private void parseEnchantment(final ItemStack itemStack, final Item item) {
        if (!item.isEnchanted()) {
            return;
        }

        final ItemMeta meta = itemStack.getItemMeta();

        meta.addEnchant(Enchantment.KNOCKBACK, 1, false);

        itemStack.setItemMeta(meta);

    }

}
