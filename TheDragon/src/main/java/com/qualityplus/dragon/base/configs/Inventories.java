package com.qualityplus.dragon.base.configs;

import com.cryptomorin.xseries.XMaterial;
import com.qualityplus.assistant.inventory.CommonGUI;
import com.qualityplus.assistant.inventory.background.DefaultBackgrounds;
import com.qualityplus.assistant.util.itemstack.ItemBuilder;
import com.qualityplus.dragon.gui.guardians.DragonGuardiansGUIConfig;
import com.qualityplus.dragon.gui.altars.DragonAltarsGUIConfig;
import com.qualityplus.dragon.gui.confirm.ConfirmGUIConfig;
import com.qualityplus.dragon.gui.crystals.DragonCrystalsGUIConfig;
import com.qualityplus.dragon.gui.guardian.GuardianGUIConfig;
import com.qualityplus.dragon.gui.mainmenu.MainMenuGUIConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import eu.okaeri.platform.core.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration(path = "inventories.yml")
@Header("================================")
@Header("       Inventories      ")
@Header("================================")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public final class Inventories extends OkaeriConfig implements DefaultBackgrounds {

    @CustomKey("guardiansGUIConfig")
    public DragonGuardiansGUIConfig guardiansGUIConfig = new DragonGuardiansGUIConfig(
            new CommonGUI(
                    "Guardians GUI",
                    54,
                    getBackGroundFiller(),
                    ItemBuilder.of(XMaterial.BARRIER,  49, 1, "&cClose", Arrays.asList("", "&e» &7Click to close")).build()
            ),
            ItemBuilder.of(XMaterial.CREEPER_HEAD, 1, "&aGuardian: &4%dragon_guardian_id%", Arrays.asList("",
                    "", "&e► Left-Click to edit", "&e► Right-Click to remove")).build(),
            ItemBuilder.of(XMaterial.ARROW,  48, 1, "&aPrevious Page", Collections.singletonList("&7Click to go to previous page")).build(),
            ItemBuilder.of(XMaterial.ARROW,  50, 1, "&aNext Page", Collections.singletonList("&7Click to go to next page")).build()
    );

    @CustomKey("altarsGUIConfig")
    public DragonAltarsGUIConfig dragonAltarsGUIConfig = new DragonAltarsGUIConfig(
            new CommonGUI(
                    "Altars GUI",
                    54,
                    getBackGroundFiller(),
                    ItemBuilder.of(XMaterial.BARRIER,  49, 1, "&cClose", Arrays.asList("", "&e» &7Click to close")).build()
            ),
            ItemBuilder.of(XMaterial.PLAYER_HEAD, 1, "&aAltar: &4%dragon_altar_id%", Arrays.asList("", "&eLocation: &6%dragon_crystal_location%", "", "&e► Left-Click to teleport", "&e► Right-Click to remove"))
                    .headData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjQ2ODRlM2U3ODkwY2FmN2QxMzc2MmVhMTllYjE0YzU5NDBiODhmZDdmMDc3ZDgxZTZlZmZiNGY2ZGYxNmMyNiJ9fX0=").build(),
            ItemBuilder.of(XMaterial.ARROW,  48, 1, "&aPrevious Page", Collections.singletonList("&7Click to go to previous page")).build(),
            ItemBuilder.of(XMaterial.ARROW,  50, 1, "&aNext Page", Collections.singletonList("&7Click to go to next page")).build(),
            ItemBuilder.of(XMaterial.ARROW,  45, 1, "&aGo Back", Collections.singletonList("&7Click to go to main menu")).build()

    );

    @CustomKey("confirmGUIConfig")
    public ConfirmGUIConfig confirmGUIConfig = new ConfirmGUIConfig(
            new CommonGUI(
                    "Confirm GUI",
                    27,
                    getBackGroundFiller(),
                    ItemBuilder.of(XMaterial.BARRIER,  49, 1, "&cClose", Arrays.asList("", "&e» &7Click to close")).enabled(false).build()
            ),
            ItemBuilder.of(XMaterial.LIME_WOOL,  11, 1, "&aConfirm", Collections.singletonList("&7Click to confirm")).build(),
            ItemBuilder.of(XMaterial.RED_WOOL, 15, 1, "&cCancel", Collections.singletonList("&7Click to cancel")).build()
    );

    @CustomKey("crystalsGUIConfig")
    public DragonCrystalsGUIConfig dragonCrystalsGUIConfig = new DragonCrystalsGUIConfig(
            new CommonGUI(
                    "Crystals GUI",
                    54,
                    getBackGroundFiller(),
                    ItemBuilder.of(XMaterial.BARRIER,  49, 1, "&cClose", Arrays.asList("", "&e» &7Click to close")).build()
            ),
            ItemBuilder.of(XMaterial.PLAYER_HEAD, 1, "&aCrystal: &4%dragon_crystal_id%", Arrays.asList("", "&eLocation: &6%dragon_crystal_location%", "", "&e► Left-Click to teleport", "&e► Right-Click to remove"))
                    .headData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjc0ODEzOWVlNmNmZjc0NGM3ZTg5NTkzZjZiOTBkNDYwNDRkMDdlZTVlNjM4MjhmYmU5NTMxZmI2NmRmOWI4ZiJ9fX0=").build(),
            ItemBuilder.of(XMaterial.ARROW,  48, 1, "&aPrevious Page", Collections.singletonList("&7Click to go to previous page")).build(),
            ItemBuilder.of(XMaterial.ARROW,  50, 1, "&aNext Page", Collections.singletonList("&7Click to go to next page")).build(),
            ItemBuilder.of(XMaterial.ARROW,  45, 1, "&aGo Back", Collections.singletonList("&7Click to go to main menu")).build()

    );


    @CustomKey("mainMenuGUIConfig")
    public MainMenuGUIConfig mainMenuGUIConfig = new MainMenuGUIConfig(
            new CommonGUI(
                    "Main GUI",
                    54,
                    getBackGroundFiller(),
                    ItemBuilder.of(XMaterial.BARRIER,  49, 1, "&cClose", Arrays.asList("", "&e» &7Click to close")).build()
            ),
            ItemBuilder.of(XMaterial.DRAGON_WALL_HEAD, 4, 1, "&aDragon Game Spawn", Arrays.asList("", "&eLocation: &6%dragon_spawn_location%", "", "&e► Click to teleport")).build(),
            ItemBuilder.of(XMaterial.PLAYER_HEAD,  19, 1, "&aGame Crystals", Arrays.asList("", "&eAmount: &6%dragon_crystals_amount%", "", "&e► Click to view")).headData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjc0ODEzOWVlNmNmZjc0NGM3ZTg5NTkzZjZiOTBkNDYwNDRkMDdlZTVlNjM4MjhmYmU5NTMxZmI2NmRmOWI4ZiJ9fX0=").build(),
            ItemBuilder.of(XMaterial.PLAYER_HEAD,  22, 1, "&aGame Altars", Arrays.asList("", "&eAmount: &6%dragon_altars_amount%", "", "&e► Click to view")).headData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjQ2ODRlM2U3ODkwY2FmN2QxMzc2MmVhMTllYjE0YzU5NDBiODhmZDdmMDc3ZDgxZTZlZmZiNGY2ZGYxNmMyNiJ9fX0").build(),
            ItemBuilder.of(XMaterial.PAPER, 25, 1, "&aCurrent Schematic", Arrays.asList("", "&e► Schematic: %dragon_schematic_id%")).build()

    );

    @CustomKey("guardianGUIConfig")
    public GuardianGUIConfig guardianGUIConfig = new GuardianGUIConfig(
            new CommonGUI(
                    "Guardian GUI",
                    54,
                    getBackGroundFiller(),
                    ItemBuilder.of(XMaterial.BARRIER,  49, 1, "&cClose", Arrays.asList("", "&e» &7Click to close")).build()
            ),
            ItemBuilder.of(XMaterial.ARROW, 48, 1, "&aGo Back", Arrays.asList("", "&7Click to go back")).build(),
            ItemBuilder.of(XMaterial.DIAMOND_HELMET, 1, "&aEdit Helmet", Arrays.asList("", "&7 ► Click to change")).build(),
            ItemBuilder.of(XMaterial.DIAMOND_CHESTPLATE, 1, "&aEdit Chestplate", Arrays.asList("", "&7 ► Click to change")).build(),
            ItemBuilder.of(XMaterial.DIAMOND_LEGGINGS, 1, "&aEdit Leggings", Arrays.asList("", "&7 ► Click to change")).build(),
            ItemBuilder.of(XMaterial.DIAMOND_BOOTS, 1, "&aEdit Boots", Arrays.asList("", "&7 ► Click to change")).build(),
            ItemBuilder.of(XMaterial.ZOMBIE_HEAD, 22, 1, "&aMob Type", Arrays.asList("", "&eType: &6%type%", "", "&e► Click to change")).build(),
            ItemBuilder.of(XMaterial.NAME_TAG, 22, 1, "&aDisplayName", Arrays.asList("", "&eDisplayname: &6%displayname%", "", "&e► Click to change")).build(),
            ItemBuilder.of(XMaterial.APPLE, 1, "&aHealth: %health%", Arrays.asList("", "&7 ► Click to change")).build()
    );
}
