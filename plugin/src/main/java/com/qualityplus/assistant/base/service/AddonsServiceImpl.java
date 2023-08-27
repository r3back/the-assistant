package com.qualityplus.assistant.base.service;

import com.qualityplus.assistant.api.addons.EconomyAddon;
import com.qualityplus.assistant.api.addons.MMOItemsAddon;
import com.qualityplus.assistant.api.addons.MythicMobsAddon;
import com.qualityplus.assistant.api.addons.NPCAddon;
import com.qualityplus.assistant.api.addons.PasterAddon;
import com.qualityplus.assistant.api.addons.PlaceholdersAddon;
import com.qualityplus.assistant.api.addons.RegionAddon;
import com.qualityplus.assistant.api.addons.WorldManagerAddon;
import com.qualityplus.assistant.api.dependency.DependencyPlugin;
import com.qualityplus.assistant.api.service.AddonsService;
import eu.okaeri.commons.bukkit.time.MinecraftTimeEquivalent;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.bukkit.annotation.Delayed;
import eu.okaeri.platform.core.annotation.Component;
import lombok.Getter;

import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Addons Service Implementation
 */
@Getter
@Component
public final class AddonsServiceImpl implements AddonsService {
    private static final String SUCCESSFULLY_HOOKED_MESSAGE = "Successfully hooked into %s!";
    private @Inject WorldManagerAddon worldManager;
    private @Inject PlaceholdersAddon placeholders;
    private @Inject MythicMobsAddon mythicMobs;
    private @Inject MMOItemsAddon mmoItems;
    private @Inject EconomyAddon economy;
    private @Inject RegionAddon regions;
    private @Inject PasterAddon paster;
    private @Inject NPCAddon npc;
    private @Inject Logger log;

    /**
     * Send a log with hooked dependencies
     */
    @Delayed(time = MinecraftTimeEquivalent.SECOND / 20, async = true)
    public void showAddons() {
        Stream.of(this.placeholders, this.mmoItems, this.economy,
                  this.regions, this.paster, this.npc,
                  this.mythicMobs, this.worldManager)
                .map(DependencyPlugin::getAddonName)
                .filter(Objects::nonNull)
                .forEach(this::sendHookMessage);
    }

    private void sendHookMessage(final String name) {
        this.log.info(String.format(SUCCESSFULLY_HOOKED_MESSAGE, name));
    }
}
