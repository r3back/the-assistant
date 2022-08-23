package com.qualityplus.skills.base.skill.skills;

import com.qualityplus.skills.TheSkills;
import com.qualityplus.skills.base.skill.Skill;
import com.qualityplus.skills.base.skill.gui.GUIOptions;
import com.qualityplus.assistant.api.common.rewards.commands.CommandRewards;
import com.qualityplus.skills.base.reward.StatRewards;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public final class FishingSkill extends Skill {
    private Map<String, Double> rewards;
    private double rewardsForAllCaught;

    @Builder
    public FishingSkill(String id, boolean enabled, String displayName, List<String> description, StatRewards statRewards, CommandRewards commandRewards,
                        GUIOptions skillGUIOptions, Map<Integer, List<String>> skillsInfoInGUI, Map<Integer, List<String>> skillsInfoInMessage,
                        Map<Integer, Double> xpRequirements, int maxLevel, Map<String, Double> rewards, double rewardsForAllCaught) {
        super(id, enabled, displayName, description, maxLevel, statRewards, commandRewards, skillGUIOptions, xpRequirements, skillsInfoInGUI, skillsInfoInMessage);

        this.rewardsForAllCaught = rewardsForAllCaught;
        this.rewards = rewards;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void fishingSkill(PlayerFishEvent e) {
        Player player = e.getPlayer();

        String caughtName = Optional.ofNullable(e.getCaught()).map(Entity::getName).map(String::toUpperCase).orElse(null);

        State state = e.getState();

        if(caughtName == null) return;

        if (state != State.CAUGHT_FISH && state != State.CAUGHT_ENTITY) return;

        if (state == State.CAUGHT_ENTITY && !rewards.containsKey(caughtName)) return;

        double xp = rewardsForAllCaught + rewards.getOrDefault(caughtName, 0d);

        if(xp <= 0) return;

        TheSkills.getApi().getSkillsService().addXp(player, true, true, this, xp);

    }
}
