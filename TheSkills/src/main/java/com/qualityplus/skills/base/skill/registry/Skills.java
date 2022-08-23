package com.qualityplus.skills.base.skill.registry;

import com.google.common.collect.ImmutableSet;
import com.qualityplus.skills.api.box.Box;
import com.qualityplus.skills.base.skill.Skill;
import eu.okaeri.commons.bukkit.time.MinecraftTimeEquivalent;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.bukkit.annotation.Delayed;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.Bukkit;
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
public final class Skills {
    private static final Map<String, Skill> SKILL_REGISTRY = new HashMap<>();

    @ApiStatus.Internal
    public static void registerNewSkill(@NotNull final Skill skill) {
        SKILL_REGISTRY.put(skill.getId().toLowerCase(), skill);
    }

    @Nullable
    public static Skill getByID(@NotNull final String id) {
        return SKILL_REGISTRY.get(id.toLowerCase());
    }

    @Nullable
    public static Skill getByKey(@NotNull final NamespacedKey key) {
        return SKILL_REGISTRY.get(key.getKey());
    }

    public static Set<Skill> values() {
        return ImmutableSet.copyOf(SKILL_REGISTRY.values());
    }

    public static Set<Skill> values(Predicate<Skill> filter) {
        return ImmutableSet.copyOf(SKILL_REGISTRY.values().stream().filter(filter).collect(Collectors.toList()));
    }

    @Delayed(time = MinecraftTimeEquivalent.SECOND, async = true)
    public static void reloadSkills(@Inject Box box){
        values().forEach(Skill::unregisterListeners);
        box.skillFiles().dungeoneering().dungeoneeringSkill.register();
        box.skillFiles().runeCrafting().runecraftingSkill.register();
        box.skillFiles().discoverer().discovererSkill.register();
        box.skillFiles().enchanting().enchantingSkill.register();
        box.skillFiles().carpentry().carpentrySkill.register();
        box.skillFiles().foraging().foragingSkill.register();
        box.skillFiles().alchemy().alchemySkill.register();
        box.skillFiles().farming().farmingSkill.register();
        box.skillFiles().fishing().fishingSkill.register();
        box.skillFiles().combat().combatSkill.register();
        box.skillFiles().mining().miningSkill.register();
        box.skillFiles().taming().tamingSkill.register();

        values(Skill::isEnabled).forEach(skill -> skill.registerListeners(box));

    }
}
