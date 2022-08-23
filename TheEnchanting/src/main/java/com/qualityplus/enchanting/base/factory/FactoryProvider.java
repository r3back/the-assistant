package com.qualityplus.enchanting.base.factory;

import com.qualityplus.assistant.TheAssistantPlugin;
import com.qualityplus.enchanting.api.provider.EnchantmentProvider;
import com.qualityplus.enchanting.base.provider.DefaultEnchantmentProvider;
import com.qualityplus.enchanting.base.provider.EcoProvider;
import eu.okaeri.injector.OkaeriInjector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Bean;
import eu.okaeri.platform.core.annotation.Component;

@Component
public final class FactoryProvider {
    private @Inject("injector") OkaeriInjector injector;

    @Bean("ecoProvider")
    private EnchantmentProvider setupEco(){
        if(TheAssistantPlugin.getAPI().getDependencyResolver().isPlugin("EcoEnchants"))
            return injector.createInstance(EcoProvider.class);
        else
            return injector.createInstance(DefaultEnchantmentProvider.class);
    }
}
