package com.qualityplus.assistant.util.location;

import com.qualityplus.assistant.api.util.IPlaceholder;
import com.qualityplus.assistant.util.StringUtils;
import com.qualityplus.assistant.api.util.NumberUtil;
import com.qualityplus.assistant.util.placeholder.Placeholder;
import com.qualityplus.assistant.util.placeholder.PlaceholderBuilder;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;
import java.util.Optional;

/**
 * Utility class for Locations
 */
@UtilityClass
public final class LocationUtils {
    private static final String FULL_LOCATION_FORMAT = "X: %x%, Y: %y%, Z: %z%, Yaw: %yaw%, Pitch: %pitch%, World: %world%";
    private static final String LOCATION_FORMAT = "X: %x%, Y: %y%, Z: %z%, World: %world%";
    private static final String NULL_LOCATION_FORMAT = "&c✘";

    /**
     * Serialize full location data to string
     *
     * @param location {@link Location}
     * @return full location data
     */
    public String fullToString(final Location location) {
        if (location == null) {
            return NULL_LOCATION_FORMAT;
        }

        final String world = getWorld(location);

        final List<IPlaceholder> placeholders = PlaceholderBuilder
                .create(new Placeholder("world", world),
                        new Placeholder("x", location.getX()),
                        new Placeholder("y", location.getY()),
                        new Placeholder("yaw", location.getYaw()),
                        new Placeholder("pitch", location.getPitch()),
                        new Placeholder("z", location.getZ()))
                .get();

        return StringUtils.processMulti(FULL_LOCATION_FORMAT, placeholders);
    }

    /**
     * Serialize basic location data to string
     *
     * @param location {@link Location}
     * @return basic location data
     */
    public String toString(final Location location) {
        if (location == null) {
            return NULL_LOCATION_FORMAT;
        }

        final String world = getWorld(location);

        final List<IPlaceholder> placeholders = PlaceholderBuilder
                .create(new Placeholder("world", world),
                        new Placeholder("x", location.getX()),
                        new Placeholder("y", location.getY()),
                        new Placeholder("z", location.getZ()))
                .get();

        return StringUtils.processMulti(LOCATION_FORMAT, placeholders);
    }

    /**
     * Deserialize string location
     *
     * @param from {@link Location}
     * @return {@link Location}
     */
    public Location fromString(final String from) {
        final String[] separated = from.split(",");

        final double[] coords = new double[3];

        String world = null;

        int count = 0;

        for (final String value : separated) {
            if (value.contains(" World: ")) {
                world = value.replaceAll(" World: ", "");
            } else {
                coords[count] = NumberUtil.extractNumbers(value);
            }
            count++;
        }

        return Optional.ofNullable(world)
                .map(w -> new Location(Bukkit.getWorld(w), coords[0], coords[1], coords[2]))
                .orElse(null);
    }

    private String getWorld(final Location location) {
        //Don"t change this to prevent getName taken from WorldInfo
        return Optional.ofNullable(location.getWorld())
                .map(world1 -> world1.getName())
                .orElse(NULL_LOCATION_FORMAT);
    }
}
