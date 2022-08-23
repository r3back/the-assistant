package com.qualityplus.skills.base.stat.registry;

import com.google.common.collect.ImmutableSet;
import com.qualityplus.skills.api.box.Box;
import com.qualityplus.skills.base.stat.Stat;
import eu.okaeri.commons.bukkit.time.MinecraftTimeEquivalent;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.bukkit.annotation.Delayed;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public final class Stats {
    private static final Map<String, Stat> STAT_REGISTRY = new HashMap<>();

    @ApiStatus.Internal
    public static void registerNewStat(@NotNull final Stat stat) {
        STAT_REGISTRY.put(stat.getId().toLowerCase(), stat);
    }

    @Nullable
    public static Stat getByID(@NotNull final String id) {
        return STAT_REGISTRY.get(id.toLowerCase());
    }

    @Nullable
    public static Stat getByKey(@NotNull final NamespacedKey key) {
        return STAT_REGISTRY.get(key.getKey());
    }

    public static Set<Stat> values() {
        return ImmutableSet.copyOf(STAT_REGISTRY.values());
    }

    public static Set<Stat> values(Predicate<Stat> filter) {
        return ImmutableSet.copyOf(STAT_REGISTRY.values().stream().filter(filter).collect(Collectors.toList()));
    }

    @Delayed(time = MinecraftTimeEquivalent.SECOND, async = true)
    public static void reloadStats(@Inject Box box){
        values().forEach(Stat::unregisterListeners);

        box.statFiles().defense().defenseStat.register();
        box.statFiles().intelligence().intelligenceStat.register();
        box.statFiles().critChance().critChanceStat.register();
        box.statFiles().critDamage().critDamageStat.register();
        box.statFiles().magicFind().magicFindStat.register();
        box.statFiles().mining().miningSpeedPerk.register();
        box.statFiles().ferocity().ferocityStat.register();
        box.statFiles().petLuck().petLuckStat.register();
        box.statFiles().speed().speedStat.register();
        box.statFiles().strength().strengthStat.register();

        values(Stat::isEnabled).forEach(skill -> skill.registerListeners(box));
    }
}
