package com.qualityplus.dragon.base.game.reward;

import com.qualityplus.dragon.api.game.reward.DragonReward;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public final class DragonRewardImpl implements DragonReward {
    private final int damageDone;
    private final List<String> commands;

    @Override
    public int getDamageDone() {
        return damageDone;
    }

    @Override
    public List<String> getCommands() {
        return commands;
    }
}
