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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.enchantment.EnchantItemEvent;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public final class EnchantingSkill extends Skill {
    private Map<Integer, Double> rewardsPerLevel;
    private double rewardForAllEnchantments;

    @Builder
    public EnchantingSkill(String id, boolean enabled, String displayName, List<String> description, StatRewards statRewards, CommandRewards commandRewards,
                           GUIOptions skillGUIOptions, Map<Integer, List<String>> skillsInfoInGUI, Map<Integer, List<String>> skillsInfoInMessage,
                           Map<Integer, Double> xpRequirements, int maxLevel, Map<Integer, Double> rewardsPerLevel, double rewardForAllEnchantments) {
        super(id, enabled, displayName, description, maxLevel, statRewards, commandRewards, skillGUIOptions, xpRequirements, skillsInfoInGUI, skillsInfoInMessage);

        this.rewardForAllEnchantments = rewardForAllEnchantments;
        this.rewardsPerLevel = rewardsPerLevel;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEnchantItemEvent(EnchantItemEvent e) {
        Player player = e.getEnchanter();

        double xp = rewardForAllEnchantments + getExtraXp(e);

        if(xp <= 0) return;

        TheSkills.getApi().getSkillsService().addXp(player, true, true, this, xp);
    }

    private double getExtraXp(EnchantItemEvent event){
        double xp = 0;
        for(Integer i : event.getEnchantsToAdd().values())
            xp+=rewardsPerLevel.getOrDefault(i, 0d);
        return xp;
    }
}
