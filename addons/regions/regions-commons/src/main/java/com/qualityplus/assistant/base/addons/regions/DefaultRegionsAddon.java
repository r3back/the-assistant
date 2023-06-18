package com.qualityplus.assistant.base.addons.regions;

import com.qualityplus.assistant.api.addons.RegionAddon;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

/**
 * Default Region addon implementation
 */
public final class DefaultRegionsAddon implements RegionAddon {
    @Override
    public Set<String> getRegions(final Location location) {
        return new HashSet<>();
    }

    @Override
    public String getAddonName() {
        return null;
    }
}
