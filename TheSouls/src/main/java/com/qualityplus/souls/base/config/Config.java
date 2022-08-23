package com.qualityplus.souls.base.config;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.cryptomorin.xseries.particles.XParticle;
import com.qualityplus.assistant.api.gui.LoreWrapper;
import com.qualityplus.assistant.config.ConfigDatabase;
import com.qualityplus.assistant.config.ConfigSound;
import com.qualityplus.assistant.inventory.Item;
import com.qualityplus.assistant.util.itemstack.ItemBuilder;
import com.qualityplus.assistant.util.sound.SoundUtils;
import com.qualityplus.assistant.util.time.Timer;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import eu.okaeri.platform.core.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Configuration()
@Header("================================")
@Header("       Config      ")
@Header("================================")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public final class Config extends OkaeriConfig {
    public String prefix = "[TheSouls] ";

    @CustomKey("configDatabase")
    @Comment("Database Configuration")
    @Comment("Allowed Database Types")
    @Comment("- H2")
    @Comment("- FLAT")
    @Comment("- MYSQL")
    @Comment("- REDIS")
    public ConfigDatabase configDatabase = new ConfigDatabase();

    @CustomKey("loreWrapper")
    @Comment("- wrapLength = After how many characters in a lore you want")
    @Comment("               to separate it.")
    @Comment("- wrapStart = After line is separated what character do you")
    @Comment("              want to start the new line with.")
    public LoreWrapper loreWrapper = new LoreWrapper(50, "&7");

    public Item soulItem = ItemBuilder.of(XMaterial.PLAYER_HEAD, 1, "&dFairy Soul &e(Right Click to Place)", Arrays.asList(""))
            .headData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjk2OTIzYWQyNDczMTAwMDdmNmFlNWQzMjZkODQ3YWQ1Mzg2NGNmMTZjMzU2NWExODFkYzhlNmIyMGJlMjM4NyJ9fX0=")
            .build();

    public ConfigSound soulFoundSound = new ConfigSound(XSound.ENTITY_EXPERIENCE_ORB_PICKUP, true, 0.2f, 1f);
    public ConfigSound soulAlreadyFoundSound = new ConfigSound(XSound.BLOCK_ANVIL_DESTROY, true, 0.2f, 1f);
    public ConfigSound allSoulsFoundSound = new ConfigSound(XSound.ENTITY_PLAYER_LEVELUP, true, 0.2f, 1f);

    public String constantSoulParticle = "SPELL_WITCH";
    public String foundSoulParticle = "SPELL_WITCH";

    public List<String> soulFoundCommands = Collections.singletonList("lp user %player% set permission permission.per true");
    public List<String> firstSoulFoundCommands = Collections.emptyList();
    public List<String> allSoulsFoundCommands = Collections.singletonList("money give %player% 3000");

}
