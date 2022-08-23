package com.qualityplus.skills.base.perk.perks;

import com.cryptomorin.xseries.XPotion;
import com.qualityplus.assistant.base.event.EntityDamagedByPlayerEvent;
import com.qualityplus.assistant.util.math.MathUtils;
import com.qualityplus.skills.base.perk.perks.common.AbstractPotionPerk;
import com.qualityplus.skills.base.skill.gui.GUIOptions;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public final class WizardPerk extends AbstractPotionPerk {
    @Builder
    public WizardPerk(String id, boolean enabled, String displayName, List<String> description, GUIOptions skillGUIOptions, double initialAmount, double chancePerLevel, int secondsDurationPerLevel,
                      int baseSecondsDuration, int level) {
        super(id, enabled, displayName, description, skillGUIOptions, initialAmount, chancePerLevel, secondsDurationPerLevel, baseSecondsDuration, level);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void handlePerk(EntityDamagedByPlayerEvent e) {
        Player p = e.getPlayer();

        if (MathUtils.randomBetween(0.0, 100.0) >= getChancePerLevel() * getStat(p))
            return;

        Optional.of(XPotion.CONFUSION)
                .map(potion -> potion.parsePotion(getDurationTicks(getStat(p)), getLevel()))
                .ifPresent(p::addPotionEffect);
    }
}
