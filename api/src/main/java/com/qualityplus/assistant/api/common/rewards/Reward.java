package com.qualityplus.assistant.api.common.rewards;

import org.bukkit.entity.Player;

/**
 * Executable player reward
 */
public interface Reward {
    /**
     * Execute reward
     *
     * @param player {@link Player}
     */
    public void execute(final Player player);
}
