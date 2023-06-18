package com.qualityplus.assistant.util.faster;

import com.google.common.collect.ImmutableMap;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utility class to create quickly immutable maps
 */
@UtilityClass
public final class FastMap {
    /**
     * Retrieves a map with an unique value
     *
     * @param clazz    {@link Class} of generic key class
     * @param key      {@link Class} of generic value class
     * @param keyValue generic key value
     * @param value    generic value
     * @return {@link Map}
     * @param <T> Generic Key
     * @param <K> Generic Value
     */
    public <T, K> Map<T, K> of(final Class<T> clazz, final Class<K> key, final T keyValue, final K value) {
        return ImmutableMap.<T, K>builder()
                .put(keyValue, value)
                .build();
    }

    /**
     * Retrieves a map given a set of entries
     *
     * @param clazz    {@link Class} of generic key class
     * @param key      {@link Class} of generic value class
     * @param set      Set of {@link FastEntry}
     * @return {@link Map}
     * @param <T> Generic Key
     * @param <K> Generic Value
     */
    public <T, K> Map<T, K> of(final Class<T> clazz, final Class<K> key, final Set<FastEntry<T, K>> set) {
        return ImmutableMap.<T, K>builder()
                .putAll(set)
                .build();
    }

    /**
     * Retrieves a map build given a generic key and value
     *
     * @param key   {@link Class} of generic key class
     * @param value {@link Class} of generic value class
     * @return {@link ImmutableMap.Builder}
     * @param <T> Generic Key
     * @param <K> Generic Value
     */
    public <T, K> ImmutableMap.Builder<T, K> builder(final Class<T> key, final Class<K> value) {
        return ImmutableMap.<T, K>builder();
    }

    /**
     * Retrieves a map with list values given a generic key and value
     *
     * @param key   {@link Class} of generic key class
     * @param value {@link Class} of generic value class
     * @return {@link ImmutableMap.Builder}
     * @param <T> Generic Key
     * @param <K> Generic Value
     */
    public <T, K> ImmutableMap.Builder<T, List<K>> listBuilder(final Class<T> key, final Class<K> value) {
        return ImmutableMap.<T, List<K>>builder();
    }

    /**
     * Retrieves an emtpty map
     *
     * @return {@link Map}
     * @param <T> Generic Key
     * @param <K> Generic Value
     */
    public <T, K> Map<T, K> empty() {
        return ImmutableMap.<T, K>builder().build();
    }
}
