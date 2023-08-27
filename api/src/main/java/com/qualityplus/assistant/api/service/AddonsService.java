package com.qualityplus.assistant.api.service;

import com.qualityplus.assistant.api.addons.EconomyAddon;
import com.qualityplus.assistant.api.addons.MMOItemsAddon;
import com.qualityplus.assistant.api.addons.MythicMobsAddon;
import com.qualityplus.assistant.api.addons.NPCAddon;
import com.qualityplus.assistant.api.addons.PasterAddon;
import com.qualityplus.assistant.api.addons.PlaceholdersAddon;
import com.qualityplus.assistant.api.addons.RegionAddon;
import com.qualityplus.assistant.api.addons.WorldManagerAddon;

/**
 * Addons Service
 */
public interface AddonsService {
    /**
     * Retrieves World manager addon
     *
     * @return {@link WorldManagerAddon}
     */
    public WorldManagerAddon getWorldManager();

    /**
     * Retrieves Placeholders addon
     *
     * @return {@link PlaceholdersAddon}
     */
    public PlaceholdersAddon getPlaceholders();

    /**
     * Retrieves MythicMobs Addon
     *
     * @return {@link MythicMobsAddon}
     */
    public MythicMobsAddon getMythicMobs();

    /**
     * Retrieves MMOItems Addon
     *
     * @return {@link MMOItemsAddon}
     */
    public MMOItemsAddon getMmoItems();

    /**
     * Retrieves Economy addon
     *
     * @return {@link EconomyAddon}
     */
    public EconomyAddon getEconomy();

    /**
     * Retrieves Regions Addon
     *
     * @return {@link RegionAddon}
     */
    public RegionAddon getRegions();

    /**
     * Retrieves Paster Addon
     *
     * @return {@link PasterAddon}
     */
    public PasterAddon getPaster();

    /**
     * Retrieves NPC Manager Addon
     *
     * @return {@link NPCAddon}
     */
    public NPCAddon getNpc();
}
