package com.qualityplus.assistant.api.common.rewards;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Levellable rewards
 *
 * @param <T> Generic reward type
 */
public interface LevellableRewards<T> {
    /**
     * Get all rewards as a map of level-reward
     *
     * @return Map that has list of rewards per level
     */
    public Map<Integer, List<T>> getRewards();

    /**
     * Retrieve Rewards per specific level
     *
     * @param level level
     * @return List of rewards for level
     */
    public default List<T> getRewardsForLevel(final Integer level) {
        return getRewards().getOrDefault(level, Collections.emptyList());
    }
}
