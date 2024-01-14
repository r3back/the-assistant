package com.qualityplus.assistant.util.map;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Utility class for Maps
 */
@UtilityClass
public class MapUtils {
    /**
     * Retrieves the input map or an empty map if it's null
     *
     * @param map input map
     * @return input map or empty map
     * @param <T> Generic Map key
     * @param <K> Generic Map value
     */
    public @NotNull <T, K> Map<T, K> check(final @Nullable Map<T, K> map) {
        return Optional.ofNullable(map)
                .orElse(new HashMap<>());
    }

    /**
     * Retrieves the input map or the alternative map if it's null
     *
     * @param map input map
     * @param or  alternative map
     * @return input map or alternative map
     * @param <T> Generic Map key
     * @param <K> Generic Map value
     */
    public @NotNull <T, K> Map<T, K> checkOr(final @Nullable Map<T, K> map, final Map<T, K> or) {
        return Optional.ofNullable(map)
                .orElse(or);
    }
}
