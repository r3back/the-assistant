package com.qualityplus.assistant.util.faster;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Faster Map Entry implementation
 *
 * @param <T> Generic Key
 * @param <K> Generic Value
 */
@Getter
@Setter
@AllArgsConstructor
public final class FastEntry<T, K> implements Map.Entry<T, K> {
    private T key;
    private K value;

    /**
     * Creates quickly an entry given a key and value
     *
     * @param key   entry key
     * @param value entry value
     * @return {@link FastEntry}
     * @param <T> Generic Key
     * @param <K> Generic Value
     */
    public static <T, K> FastEntry<T, K> of(final T key, final K value) {
        return new FastEntry<>(key, value);
    }

    /**
     * Retrieves a Set of fast entries
     *
     * @param value Array of {@link FastEntry}
     * @return Set of {@link FastEntry}
     * @param <T> Generic Key
     * @param <K> Generic Value
     */
    @SafeVarargs
    public static <T, K> Set<FastEntry<T, K>> setOf(final FastEntry<T, K>... value) {
        return new HashSet<>(Arrays.asList(value));
    }

    /**
     * Update entry's value
     *
     * @param value new value to be stored in this entry
     * @return entry
     */
    @Override
    public K setValue(final K value) {
        this.value = value;
        return value;
    }
}