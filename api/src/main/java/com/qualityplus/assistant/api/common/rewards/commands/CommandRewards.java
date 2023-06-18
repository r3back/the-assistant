package com.qualityplus.assistant.api.common.rewards.commands;

import com.qualityplus.assistant.api.common.rewards.LevellableRewards;
import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command rewards
 */
@AllArgsConstructor
@Getter @Setter
public final class CommandRewards extends OkaeriConfig implements LevellableRewards<CommandReward> {
    private Map<Integer, List<CommandReward>> rewards = new HashMap<>();
}
