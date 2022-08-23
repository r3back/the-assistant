package com.qualityplus.enchanting;

import com.qualityplus.enchanting.api.TheEnchantingAPI;
import com.qualityplus.enchanting.base.config.RecipesFile;
import com.qualityplus.assistant.okaeri.OkaeriSilentPlugin;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Scan;
import eu.okaeri.platform.core.plan.ExecutionPhase;
import eu.okaeri.platform.core.plan.Planned;
import lombok.Getter;

@Scan(deep = true)
public final class TheEnchanting extends OkaeriSilentPlugin {
    private static @Inject @Getter TheEnchantingAPI api;

    @Planned(ExecutionPhase.PRE_SHUTDOWN)
    private void saveOnShutdown(@Inject RecipesFile recipesFile) {
        recipesFile.saveRecipes();
    }
}
