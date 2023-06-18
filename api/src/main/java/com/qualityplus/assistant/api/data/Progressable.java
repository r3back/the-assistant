package com.qualityplus.assistant.api.data;

import com.qualityplus.assistant.api.util.MathUtil;

import java.util.Map;

/**
 * Progressable interface
 *
 * @param <T> Generic key type
 * @param <N> Generic xp type
 */
public interface Progressable<T, N extends Number> {
    /**
     * Return key-value map of levels
     *
     * @return Map of levels
     */
    public Map<T, N> getXP();

    /**
     * Retrieves level for specific level
     *
     * @param key key
     * @return level for key
     */
    public N getXp(final T key);

    /**
     * Add level for specific key
     *
     * @param key      key
     * @param quantity value
     */
    public default void addXp(final T key, final N quantity) {
        getXP().computeIfPresent(key, (key2, value) -> MathUtil.sumNumbers(value, quantity));
    }

    /**
     * Removes level from specific key
     *
     * @param key      key
     * @param quantity value
     */
    public default void removeXp(final T key, final N quantity) {
        getXP().computeIfPresent(key, (key2, value) -> MathUtil.subtractNumbers(value, quantity));
    }

    /**
     * Set level for specific key
     *
     * @param key      key
     * @param quantity quantity
     */
    public default void setXp(final T key, final N quantity) {
        getXP().put(key, quantity);
    }
}
