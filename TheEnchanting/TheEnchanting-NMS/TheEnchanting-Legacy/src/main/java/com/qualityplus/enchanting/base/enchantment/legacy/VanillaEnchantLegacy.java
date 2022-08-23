package com.qualityplus.enchanting.base.enchantment.legacy;

import com.qualityplus.enchanting.api.enchantment.legacy.CoreEnchantLegacy;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

public final class VanillaEnchantLegacy extends CoreEnchantLegacy {
    @Getter private final Enchantment enchantment;

    @Builder
    public VanillaEnchantLegacy(@NotNull String identifier, Integer id, Map<Integer, String> requiredPermissionsToEnchant, Map<Integer, Double> requiredXpLevelToEnchant, int maxLevel, boolean enabled, int requiredBookShelf,
                                Map<Integer, Double> requiredMoneyToEnchant, String displayName, String description, Enchantment enchantment) {
        super(identifier, id, requiredPermissionsToEnchant, requiredXpLevelToEnchant, maxLevel, enabled, requiredBookShelf, requiredMoneyToEnchant, displayName, description);

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
    public boolean conflictsWith(Enchantment other) {
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
}
