package com.qualityplus.assistant.api.addons;

import com.qualityplus.assistant.api.dependency.DependencyPlugin;
import org.bukkit.Location;

import java.util.List;
import java.util.Set;

/**
 * Region Addon
 */
public interface RegionAddon extends DependencyPlugin {
    /**
     * Retrieves if a location is inside any of given regions
     *
     * @param location {@link Location}
     * @param regions  List of regions
     * @return true if location is in any region
     */
    public default boolean isInAnyRegion(final Location location, final List<String> regions) {
        if (regions.isEmpty()) {
            return false;
        }

        return getRegions(location)
                .stream()
                .anyMatch(regions::contains);
    }

    /**
     * Retrieves if a location is inside given region
     *
     * @param location {@link Location}
     * @param region   region
     * @return true if location is in region
     */
    public default boolean isInRegion(final Location location, final String region) {
        if (region == null) {
            return false;
        }

        return getRegions(location)
                .stream()
                .anyMatch(region::equals);
    }

    /**
     * Retrieves location's regions
     *
     * @param location {@link Location}
     * @return Set of regions
     */
    public Set<String> getRegions(final Location location);
}
