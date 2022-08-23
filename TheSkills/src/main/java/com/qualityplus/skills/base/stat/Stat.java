package com.qualityplus.skills.base.stat;

import com.qualityplus.skills.TheSkills;
import com.qualityplus.skills.api.effect.CommonObject;
import com.qualityplus.skills.api.registry.ListenerRegistrable;
import com.qualityplus.skills.base.skill.gui.GUIOptions;
import com.qualityplus.skills.base.stat.registry.Stats;
import com.qualityplus.skills.persistance.data.UserData;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;

@NoArgsConstructor
public abstract class Stat extends CommonObject implements ListenerRegistrable {
    public Stat(String id, boolean enabled, String displayName, List<String> description, GUIOptions skillGUIOptions, double baseAmount) {
        super(id, enabled, displayName, description, skillGUIOptions, baseAmount);
    }

    public void register(){
        Stats.registerNewStat(this);
    }

    public int getStat(Player player, String id){
        return TheSkills.getApi().getSkillsService().getSkillsData(player.getUniqueId())
                .map(UserData::getSkills)
                .map(userPerks -> userPerks.getLevel(id))
                .orElse(1);
    }

    public int getStat(Player player){
        return getStat(player, id);
    }
}
