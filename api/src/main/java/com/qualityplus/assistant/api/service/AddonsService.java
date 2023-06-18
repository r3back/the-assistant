package com.qualityplus.assistant.api.service;

import com.qualityplus.assistant.api.addons.*;

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