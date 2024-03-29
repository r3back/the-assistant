package com.qualityplus.assistant.base.addons.regions;

import com.qualityplus.assistant.api.addons.RegionAddon;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * World Guard 6 Implementation
 */
public final class WG6Addon implements RegionAddon {
    @Override
    public Set<String> getRegions(final Location location) {
        final RegionManager regionManager = WGBukkit.getRegionManager(location.getWorld());
        final ApplicableRegionSet set = regionManager.getApplicableRegions(location);
        return set.getRegions().stream().map(ProtectedRegion::getId).collect(Collectors.toSet());
    }

    @Override
    public String getAddonName() {
        return "World Guard 6";
    }
}
