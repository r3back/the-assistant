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
        return new HashSet<>()/*me.TechsCode.UltraRegions.UltraRegions.getInstance().getWorlds()
        .find(location.getWorld()).map(managedWorld -> UltraRegions.getInstance()
                .newRegionQuery(managedWorld)
                .location(XYZ.from(location))
                .sortBySize()
                .getRegions()
                .stream()
                .map(Region::getName)
                .collect(Collectors.toSet())).orElseGet(HashSet::new)*/;
    }

    @Override
    public String getAddonName() {
        return "Ultra Regions";
    }
}
