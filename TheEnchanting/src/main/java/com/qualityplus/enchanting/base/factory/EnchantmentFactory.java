package com.qualityplus.enchanting.base.factory;

import com.cryptomorin.xseries.XMaterial;
import com.qualityplus.enchanting.api.enchantment.ICoreEnchantment;
import com.qualityplus.enchanting.base.enchantment.legacy.VanillaEnchantLegacy;
import com.qualityplus.enchanting.base.enchantment.newest.VanillaEnchantNewest;
import lombok.Builder;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class EnchantmentFactory {
    private static final AtomicInteger ENCHANTMENT_COUNTER = new AtomicInteger(100);
    private final Map<Integer, String> requiredPermissionsToEnchant;
    //private final Map<Integer, Double> requiredXpLevelToRemove;
    private final Map<Integer, Double> requiredXpLevelToEnchant;
    private final Map<Integer, Double> requiredMoneyToEnchant;
    //private final Map<Integer, Double> requiredMoneyToRemove;

    private final int requiredBookShelf;
    private final String displayName;
    private final String description;
    private final Enchantment enchantment;
    private final String identifier;
    private final boolean enabled;
    private final int maxLevel;

    @Builder
    public EnchantmentFactory(@NotNull String identifier, Map<Integer, String> requiredPermissionsToEnchant, Map<Integer, Double> requiredXpLevelToEnchant, int maxLevel, boolean enabled, int requiredBookShelf,
                              /*Map<Integer, Double> requiredXpLevelToRemove, Map<Integer, Double> requiredMoneyToRemove,*/ Map<Integer, Double> requiredMoneyToEnchant, String displayName, String description, Enchantment enchantment) {


        this.requiredPermissionsToEnchant = requiredPermissionsToEnchant;

        this.requiredXpLevelToEnchant = requiredXpLevelToEnchant;
        //this.requiredXpLevelToRemove = requiredXpLevelToRemove;

        this.requiredMoneyToEnchant = requiredMoneyToEnchant;
        //this.requiredMoneyToRemove = requiredMoneyToRemove;

        this.requiredBookShelf = requiredBookShelf;
        this.enchantment = enchantment;
        this.displayName = displayName;
        this.description = description;
        this.identifier = identifier;
        this.maxLevel = maxLevel;
        this.enabled = enabled;
    }

    public ICoreEnchantment buildVanilla(){
        if(XMaterial.getVersion() > 12){
            return VanillaEnchantNewest.builder()
                    .requiredPermissionsToEnchant(requiredPermissionsToEnchant)
                    //.requiredXpLevelToRemove(requiredXpLevelToRemove)
                    //.requiredMoneyToRemove(requiredMoneyToRemove)
                    .requiredXpLevelToEnchant(requiredXpLevelToEnchant)

                    .requiredMoneyToEnchant(requiredMoneyToEnchant)
                    .requiredBookShelf(requiredBookShelf)
                    .enchantment(enchantment)
                    .displayName(displayName)
                    .description(description)
                    .identifier(identifier)
                    .maxLevel(maxLevel)
                    .enabled(enabled)
                    .build();
        }else{
            return VanillaEnchantLegacy.builder()
                    .requiredPermissionsToEnchant(requiredPermissionsToEnchant)
                    .requiredXpLevelToEnchant(requiredXpLevelToEnchant)
                    .requiredMoneyToEnchant(requiredMoneyToEnchant)
                    //.requiredXpLevelToRemove(requiredXpLevelToRemove)
                    //.requiredMoneyToRemove(requiredMoneyToRemove)
                    .requiredBookShelf(requiredBookShelf)
                    .displayName(displayName)
                    .description(description)
                    .identifier(identifier)
                    .maxLevel(maxLevel)
                    .enabled(enabled)
                    .id(ENCHANTMENT_COUNTER.getAndIncrement())
                    .enchantment(enchantment)
                    .build();
        }
    }
}
