package com.qualityplus.assistant.api.data;

import com.qualityplus.assistant.api.util.MathUtil;

import java.util.Map;

/**
 * Levellable interface
 *
 * @param <T> Generic key type
 * @param <N> Generic level type
 */
public interface Levellable<T, N extends Number> {
    /**
     * Return key-value map of levels
     *
     * @return Map of levels
     */
    public Map<T, N> getLevel();

    /**
     * Retrieves default value
     *
     * @return default value
     */
    public N getDefault();

    /**
     * Retrieves level for specific level
     *
     * @param key key
     * @return level for key
     */
    public default N getLevel(final T key) {
        return getLevel().getOrDefault(key, getDefault());
    }

    /**
     * Add level for specific key
     *
     * @param key      key
     * @param quantity value
     */
    public default void addLevel(final T key, final N quantity) {
        getLevel().computeIfPresent(key, (key2, value) -> MathUtil.sumNumbers(value, quantity));
    }

    /**
     * Removes level from specific key
     *
     * @param key      key
     * @param quantity value
     */
    public default void removeLevel(final T key, final N quantity) {
        getLevel().computeIfPresent(key, (key2, value) -> MathUtil.subtractNumbers(value, quantity));
    }

    /**
     * Set level for specific key
     *
     * @param key      key
     * @param quantity quantity
     */
    public default void setLevel(final T key, final N quantity) {
        getLevel().put(key, quantity);
    }
}
