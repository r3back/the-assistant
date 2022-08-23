package com.qualityplus.skills.base.config.perk;

import com.cryptomorin.xseries.XMaterial;
import com.qualityplus.skills.base.perk.perks.OnePunchManPerk;
import com.qualityplus.skills.base.serdes.registry.SerdesSkillsRegistry;
import com.qualityplus.skills.base.skill.gui.GUIOptions;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import eu.okaeri.platform.core.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;


@Configuration(path = "perks/one_punch_man.yml", serdes = SerdesSkillsRegistry.class)
@Header("================================")
@Header("       One Punch Man Perk      ")
@Header("================================")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public final class OnePunchManConfig extends OkaeriConfig {
    public OnePunchManPerk onePunchManPerk = OnePunchManPerk.builder()
                                       .id("one_punch_man")
                                       .displayName("One Punch Man")
                                       .description(Arrays.asList("  &a%percent%% &7chance to kill mobs of", "  &7one hit."))
                                       .enabled(true)
                                       .skillGUIOptions(GUIOptions.builder()
                                               .slot(23)
                                               .page(1)
                                               .item(XMaterial.PLAYER_HEAD)
                                               .texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmUyMmM2N2MwOTI3OWE4YzczMzQ4MzgzNzI4MjE5ZGEwZDEwYjU5MzI3NGYxYWIzOGNhODU3NDg3ZTMyMWU3NSJ9fX0=")
                                               .mainMenuLore(Collections.singletonList("%skill_one_punch_man_description%"))
                                               .build())
                                       .chancePerLevel(0.1)
                                       .initialAmount(0)
                                       .canBeUsedWithPlayers(false)
                                       .build();
}
