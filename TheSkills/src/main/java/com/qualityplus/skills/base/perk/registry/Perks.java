package com.qualityplus.skills.base.perk.registry;

import com.google.common.collect.ImmutableSet;
import com.qualityplus.skills.api.box.Box;
import com.qualityplus.skills.base.perk.Perk;
import eu.okaeri.commons.bukkit.time.MinecraftTimeEquivalent;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.bukkit.annotation.Delayed;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public final class Perks {
    private static final Map<String, Perk> PERK_REGISTRY = new HashMap<>();

    @ApiStatus.Internal
    public static void registerNewPerk(@NotNull final Perk perk) {
        PERK_REGISTRY.put(perk.getId().toLowerCase(), perk);
    }

    @Nullable
    public static Perk getByID(@NotNull final String id) {
        return PERK_REGISTRY.get(id.toLowerCase());
    }

    @Nullable
    public static Perk getByKey(@NotNull final NamespacedKey key) {
        return PERK_REGISTRY.get(key.getKey());
    }

    public static Set<Perk> values() {
        return ImmutableSet.copyOf(PERK_REGISTRY.values());
    }

    public static Set<Perk> values(Predicate<Perk> filter) {
        return ImmutableSet.copyOf(PERK_REGISTRY.values().stream().filter(filter).collect(Collectors.toList()));
    }

    @Delayed(time = MinecraftTimeEquivalent.SECOND, async = true)
    public static void reloadPerks(@Inject Box box){
        values().forEach(HandlerList::unregisterAll);

        box.perkFiles().abilityDamage().abilityDamagePerk.register();
        box.perkFiles().bonusAttack().bonusAttackSpeedPerk.register();
        box.perkFiles().cactusSkin().cactusSkinPerk.register();
        box.perkFiles().eagleEyes().eagleEyesPerk.register();
        box.perkFiles().farmingFortune().farmingFortuneConfig.register();
        box.perkFiles().foragingFortune().foragingFortunePerk.register();
        box.perkFiles().ironLungs().ironLungsPerk.register();
        box.perkFiles().leavesMaster().leavesMasterPerk.register();
        box.perkFiles().lightningPunch().lightningPunchPerk.register();
        box.perkFiles().medicineMan().medicineManPerk.register();
        box.perkFiles().miningFortune().miningFortunePerk.register();
        box.perkFiles().miningSpeed().miningSpeedPerk.register();
        box.perkFiles().onePunchMan().onePunchManPerk.register();
        box.perkFiles().projectileMaster().projectileMasterPerk.register();
        box.perkFiles().refurbished().refurbishedPerk.register();
        box.perkFiles().spiderman().spidermanPerk.register();
        box.perkFiles().steelSkin().steelSkinPerk.register();
        box.perkFiles().wizard().wizardPerk.register();
        box.perkFiles().seaFortune().seaFortunePerk.register();
        box.perkFiles().potionMaster().potionMasterPerk.register();
        box.perkFiles().brewChance().brewerChancePerk.register();
        box.perkFiles().orbMaster().orbMasterPerk.register();

        values().forEach(perk -> Bukkit.getPluginManager().registerEvents(perk, box.plugin()));

        box.log().info("Registered " + values().size() + " Perks!");
    }
}
