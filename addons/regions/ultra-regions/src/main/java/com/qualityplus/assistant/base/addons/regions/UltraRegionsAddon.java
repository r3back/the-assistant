package com.qualityplus.assistant.base.addons.regions;

import com.qualityplus.assistant.api.addons.RegionAddon;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

/**
 * Ultra Regions implementation
 */
public final class UltraRegionsAddon implements RegionAddon {
    @Override
    public Set<String> getRegions(final Location location) {
        return new HashSet<>();
    }

    @Override
    public String getAddonName() {
        return "Ultra Regions";
    }
}
