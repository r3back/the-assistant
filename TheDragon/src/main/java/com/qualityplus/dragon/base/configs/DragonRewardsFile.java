package com.qualityplus.dragon.base.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import eu.okaeri.platform.core.annotation.Configuration;
import com.qualityplus.dragon.base.game.reward.DragonRewardImpl;
import java.util.HashSet;
import java.util.Set;


@Configuration(path = "rewards.yml")
@Header("================================")
@Header("       Dragon Rewards      ")
@Header("================================")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public final class DragonRewardsFile extends OkaeriConfig {
    public Set<DragonRewardImpl> dragonRewards = new HashSet<>();
}
