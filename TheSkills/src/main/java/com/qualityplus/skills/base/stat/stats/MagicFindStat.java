package com.qualityplus.skills.base.stat.stats;

import com.cryptomorin.xseries.XMaterial;
import com.qualityplus.assistant.api.util.IPlaceholder;
import com.qualityplus.assistant.base.event.PlayerKillEvent;
import com.qualityplus.assistant.util.StringUtils;
import com.qualityplus.assistant.util.itemstack.ItemStackUtils;
import com.qualityplus.assistant.util.math.MathUtils;
import com.qualityplus.assistant.util.placeholder.Placeholder;
import com.qualityplus.assistant.util.placeholder.PlaceholderBuilder;
import com.qualityplus.assistant.util.random.RandomSelector;
import com.qualityplus.skills.base.event.MagicFindEvent;
import com.qualityplus.skills.base.skill.gui.GUIOptions;
import com.qualityplus.skills.base.stat.Stat;
import com.qualityplus.skills.util.SkillsPlayerUtil;
import lombok.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data @EqualsAndHashCode(callSuper = true) @NoArgsConstructor
public final class MagicFindStat extends Stat {
    private double chancePerLevel;
    private Map<XMaterial, Double> itemAndChances;

    @Builder
    public MagicFindStat(String id, boolean enabled, String displayName, List<String> description, GUIOptions skillGUIOptions, double baseAmount, double chancePerLevel, Map<XMaterial, Double> itemAndChances) {
        super(id, enabled, displayName, description, skillGUIOptions, baseAmount);

        this.chancePerLevel = chancePerLevel;
        this.itemAndChances = itemAndChances;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onKill(PlayerKillEvent e){
        Player player = e.getPlayer();

        if(!SkillsPlayerUtil.isInSurvival(player)) return;

        if(itemAndChances == null || itemAndChances.size() == 0) return;

        int level = getStat(player);

        if (MathUtils.randomBetween(0.0, 100.0) >= chancePerLevel * level)
            return;

        ItemStack toGive = RandomSelector.getRandom(itemAndChances).parseItem();

        if(ItemStackUtils.isNull(toGive)) return;

        MagicFindEvent event = new MagicFindEvent(e.getPlayer(), this, toGive, e.getKilled().getLocation());

        if(event.isCancelled()) return;

        Optional.ofNullable(event.getToDropItem()).ifPresent(item -> event.getToDropLocation().getWorld().dropItem(event.getToDropLocation(), event.getToDropItem()));
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
