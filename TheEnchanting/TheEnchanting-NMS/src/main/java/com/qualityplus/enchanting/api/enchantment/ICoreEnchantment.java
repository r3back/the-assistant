package com.qualityplus.enchanting.api.enchantment;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ICoreEnchantment {
    String getIdentifier();

    int getStartLevel();

    int getMaxLevel();

    String getName();

    String getDescription();

    boolean conflictsWith(Enchantment other);

    boolean canEnchantItem(ItemStack item);

    int getRequiredBookShelf();

    boolean isEnabled();

    double getRequiredLevelToEnchant(int level);

    double getRequiredMoneyToEnchant(int level);

    double getRequiredLevelToRemove(int level);

    double getRequiredMoneyToRemove(int level);

    boolean canEnchant(Player player, int level);

    Enchantment getEnchantment();

    default EnchantmentProvider getProvider(){
        return EnchantmentProvider.INTERNAL;
    }

    public enum EnchantmentProvider{
        INTERNAL,
        ECO_ENCHANTS,
        ADVANCED_ENCHANTMENTS;
    }
}
