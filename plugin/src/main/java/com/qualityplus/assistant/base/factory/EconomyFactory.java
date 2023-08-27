package com.qualityplus.assistant.base.factory;

import com.qualityplus.assistant.api.addons.EconomyAddon;
import com.qualityplus.assistant.api.dependency.resolver.DependencyResolver;
import com.qualityplus.assistant.base.addons.economy.DefaultEconomyAddon;
import com.qualityplus.assistant.base.addons.economy.PlayerPointsAddon;
import com.qualityplus.assistant.base.addons.economy.RoyaleEconomyAddon;
import com.qualityplus.assistant.base.addons.economy.TokenManagerAddon;
import com.qualityplus.assistant.base.addons.economy.VaultAddon;
import eu.okaeri.injector.OkaeriInjector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Bean;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.plugin.Plugin;

/**
 * Economy Addon factory
 */
@Component
public final class EconomyFactory {
    private @Inject("injector") OkaeriInjector injector;
    private @Inject DependencyResolver resolver;
    private @Inject Plugin plugin;

    /**
     * Configure economy addon
     *
     * @return {@link EconomyAddon}
     */
    @Bean
    public EconomyAddon configureEconomy() {
        if (this.resolver.isPlugin("RoyaleEconomy")) {
            return this.injector.createInstance(RoyaleEconomyAddon.class);
        } else if (this.resolver.isPlugin("Vault")) {
            return this.injector.createInstance(VaultAddon.class);
        } else if (this.resolver.isPlugin("PlayerPoints")) {
            return this.injector.createInstance(PlayerPointsAddon.class);
        } else if (this.resolver.isPlugin("TokenManager")) {
            return this.injector.createInstance(TokenManagerAddon.class);
        } else {
            return this.injector.createInstance(DefaultEconomyAddon.class);
        }
    }
}
