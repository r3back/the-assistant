package com.qualityplus.enchanting.base.provider;

import com.qualityplus.enchanting.api.enchantment.ICoreEnchantment;
import com.qualityplus.enchanting.api.provider.EnchantmentProvider;
import com.qualityplus.enchanting.base.config.enchantments.EnchantConfig;
import com.qualityplus.enchanting.base.config.enchantments.eco.EcoEnchantments;
import com.qualityplus.enchanting.base.factory.EnchantmentFactory;
import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class EcoProvider implements EnchantmentProvider {
    private @Inject EcoEnchantments config;

    @Override
    public List<ICoreEnchantment> getEnchantments() {

        return EcoEnchants.values()
                .stream()
                .map(this::build)
                .collect(Collectors.toList());
    }

    private ICoreEnchantment build(EcoEnchant ecoEnchant){
        String id = ecoEnchant.getKey().getKey();

        Optional<EnchantConfig> conf = Optional.ofNullable(config.customEcoOptions.getOrDefault(id, null));

        return EnchantmentFactory.builder()
                .identifier(id)
                .requiredPermissionsToEnchant(conf.map(EnchantConfig::getRequiredPermissionsToEnchant).orElse(new HashMap<>()))
                .requiredXpLevelToEnchant(conf.map(EnchantConfig::getRequiredXpLevelToEnchant).orElse(new HashMap<>()))
                .maxLevel(ecoEnchant.getMaxLevel())
                .enabled(true)
                .requiredBookShelf(1)
                .requiredMoneyToEnchant(conf.map(EnchantConfig::getRequiredMoneyToEnchant).orElse(new HashMap<>()))
                .displayName(ecoEnchant.getDisplayName())
                .description(ecoEnchant.getDescription())
                .enchantment(ecoEnchant)
                .build()
                .buildVanilla();
    }
}
