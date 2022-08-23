package com.qualityplus.skills.base.stat.stats;

import com.qualityplus.assistant.api.util.IPlaceholder;
import com.qualityplus.assistant.util.StringUtils;
import com.qualityplus.assistant.util.math.MathUtils;
import com.qualityplus.assistant.util.placeholder.Placeholder;
import com.qualityplus.assistant.util.placeholder.PlaceholderBuilder;
import com.qualityplus.skills.base.event.CritDamageEvent;
import com.qualityplus.skills.base.skill.gui.GUIOptions;
import com.qualityplus.skills.base.stat.Stat;
import com.qualityplus.skills.base.stat.registry.Stats;
import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;
import java.util.Optional;

/**
 * Chance to make critic damage
 */
@Data @EqualsAndHashCode(callSuper = true) @NoArgsConstructor
public final class CritChanceStat extends Stat {
    private double chancePerLevel;

    @Builder
    public CritChanceStat(String id, boolean enabled, String displayName, List<String> description, GUIOptions skillGUIOptions, double baseAmount, double chancePerLevel) {
        super(id, enabled, displayName, description, skillGUIOptions, baseAmount);

        this.chancePerLevel = chancePerLevel;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Player)) return;

        Player player = (Player) e.getDamager();

        if (MathUtils.randomBetween(0.0, 100.0) >= (chancePerLevel * getStat(player)))
            return;

        Optional<CritDamageStat> damageStat = Stats.values(stat -> stat instanceof CritDamageStat).stream()
                .map(stat -> (CritDamageStat) stat)
                .findFirst();


        double multiplier = damageStat.map(CritDamageStat::getDamagePercentagePerLevel).orElse(0D) * getStat(player, damageStat.map(CritDamageStat::getId).orElse(""));
        multiplier += initialAmount;
        multiplier /= 100;
        multiplier += 1;

        CritDamageEvent event = new CritDamageEvent(player, this);
        Bukkit.getPluginManager().callEvent(e);

        if(event.isCancelled()) return;

        e.setDamage(e.getDamage() * multiplier);
    }

    @Override
    public List<String> getFormattedDescription(int level) {
        List<IPlaceholder> placeholders = PlaceholderBuilder.create()
                .with(new Placeholder("level_number", level),
                        new Placeholder("level_roman", MathUtils.toRoman(level)),
                        new Placeholder("chance", chancePerLevel * level)
                ).get();
        return StringUtils.processMulti(description, placeholders);
    }
}
