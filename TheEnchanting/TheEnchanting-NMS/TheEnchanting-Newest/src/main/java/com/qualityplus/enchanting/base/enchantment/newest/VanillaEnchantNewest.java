package com.qualityplus.enchanting.base.enchantment.newest;

import com.qualityplus.enchanting.api.enchantment.VanillaEnchantment;
import com.qualityplus.enchanting.api.enchantment.newest.CoreEnchantNewest;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

public final class VanillaEnchantNewest extends CoreEnchantNewest implements VanillaEnchantment {
    @Getter private final Enchantment enchantment;

    @Builder
    public VanillaEnchantNewest(@NotNull String identifier, Map<Integer, String> requiredPermissionsToEnchant, Map<Integer, Double> requiredXpLevelToEnchant, int maxLevel, boolean enabled, int requiredBookShelf,
                                Map<Integer, Double> requiredMoneyToEnchant, String displayName, String description, Enchantment enchantment) {
        super(identifier, requiredPermissionsToEnchant, requiredXpLevelToEnchant, maxLevel, enabled, requiredBookShelf, requiredMoneyToEnchant, displayName, description);
        this.enchantment = enchantment;
    }

    @Override
    public @NotNull String getName() {
        return this.displayName;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return Optional.ofNullable(this.enchantment)
                .map(Enchantment::getItemTarget)
                .orElse(EnchantmentTarget.ALL);
    }

    @Override
    public boolean isTreasure() {
        return Optional.ofNullable(this.enchantment)
                .map(Enchantment::isTreasure)
                .orElse(false);
    }

    @Override
    public boolean isCursed() {
        return Optional.ofNullable(this.enchantment)
                .map(Enchantment::isCursed)
                .orElse(false);
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        return Optional.ofNullable(this.enchantment)
                .map(enchantment1 -> enchantment1.conflictsWith(other))
                .orElse(false);
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        return Optional.ofNullable(this.enchantment)
                .map(enchantment1 -> enchantment1.canEnchantItem(item))
                .orElse(false);
    }

    @Override
    public EnchantmentProvider getProvider() {
        return EnchantmentProvider.INTERNAL;
    }
}
