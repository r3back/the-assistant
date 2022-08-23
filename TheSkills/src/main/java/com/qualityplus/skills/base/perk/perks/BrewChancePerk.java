package com.qualityplus.skills.base.perk.perks;

import com.cryptomorin.xseries.XPotion;
import com.qualityplus.assistant.util.StringUtils;
import com.qualityplus.assistant.util.math.MathUtils;
import com.qualityplus.assistant.util.placeholder.Placeholder;
import com.qualityplus.assistant.util.random.EasyRandom;
import com.qualityplus.assistant.util.random.RandomSelector;
import com.qualityplus.skills.base.perk.Perk;
import com.qualityplus.skills.base.skill.gui.GUIOptions;
import lombok.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Chance to Give Random Potion Efect
 */
@Data @EqualsAndHashCode(callSuper = true) @NoArgsConstructor
public final class BrewChancePerk extends Perk {
    private Map<XPotion, Double> potionAndChances;
    private int secondsDurationPerLevel;
    private int baseSecondsDuration;

    @Builder
    public BrewChancePerk(String id, boolean enabled, String displayName, List<String> description, GUIOptions skillGUIOptions, double baseAmount, double chancePerLevel, Map<XPotion, Double> potionAndChances,
                          int baseSecondsDuration, int secondsDurationPerLevel) {
        super(id, enabled, displayName, description, skillGUIOptions, baseAmount, chancePerLevel);

        this.secondsDurationPerLevel = secondsDurationPerLevel;
        this.baseSecondsDuration = baseSecondsDuration;
        this.potionAndChances = potionAndChances;
    }

    @EventHandler(ignoreCancelled = true)
    public void playerConsumePotion(PlayerItemConsumeEvent e) {
        if(!e.getItem().getType().equals(Material.POTION)) return;

        Player p = e.getPlayer();

        if (MathUtils.randomBetween(0.0, 100.0) >= chancePerLevel * getStat(p))
            return;

        getRandom(p).ifPresent(p::addPotionEffect);
    }

    private Optional<PotionEffect> getRandom(Player player){
        List<EasyRandom<XPotion>> items = potionAndChances.keySet().stream()
                .map(item -> new EasyRandom<>(item, potionAndChances.get(item)))
                .collect(Collectors.toList());

        int level = getStat(player);

        int duration = getDurationTicks(level);

        return Optional.ofNullable(new RandomSelector<>(items).getRandom()).map(item -> item.getItem().parsePotion(duration, level));
    }

    @Override
    public List<String> getFormattedDescription(int level) {
        return StringUtils.processMulti(super.getFormattedDescription(level), Collections.singletonList(new Placeholder("duration", getDurationSeconds(level))));
    }

    private int getDurationTicks(int level){
        return getDurationSeconds(level) * 20;
    }

    private int getDurationSeconds(int level){
        return baseSecondsDuration + (secondsDurationPerLevel * level);
    }
}
