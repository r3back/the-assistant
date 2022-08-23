package com.qualityplus.dragon.base.configs;

import com.qualityplus.assistant.config.ConfigTitle;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import eu.okaeri.platform.core.annotation.Configuration;

import java.util.*;

@Configuration(path = "messages.yml")
@Header("================================")
@Header("       Messages      ")
@Header("================================")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public final class Messages extends OkaeriConfig {

    public SetupMessages setupMessages = new SetupMessages();
    public GameConfigMessages gameConfigMessages = new GameConfigMessages();
    public GameTitlesMessages gameTitlesMessages = new GameTitlesMessages();
    public PluginMessages pluginMessages = new PluginMessages();

    public class PluginMessages extends OkaeriConfig {
        public String successfullyReloaded = "&aPlugin has been reloaded successfully!";

        public String invalidPlayer = "&cInvalid Player!";

        public String useSyntax = "&cUsage: %usage%!";

        public String noPermission = "&ecYou don't have permission to do that!";
        public String unknownCommand = "&cUnknown command!";
        public String mustBeAPlayer = "&cYou must be a player to do that!";
        public String invalidArguments = "&cInvalid Arguments!";
        public String invalidAmount = "&cInvalid Amount!";
        public String useHelp = "&cUse: /dragon help";

        public String helpMessage = "&7%command% - &e%description%";
        public String helpHeader = "      &6&lTheDragon   ";
        public String helpfooter = "&e<< &6Page %page% of %maxpage% &e>>";
        public String previousPage = "<<";
        public String nextPage = ">>";
        public String helpPageHoverMessage = "Click to go to page %page%";

    }

    public static class SetupMessages extends OkaeriConfig{
        public String spawnSet = "%prefix% &7Spawn was successfully set in &6%location%&7!";
        public String crystalSet = "%prefix% &7Crystal was successfully added in &6%location%&7!";
        public String setupModeLeft = "%prefix% &aYou left from editor mode!";
        public String altarSet = "%prefix% &7New Altar was successfully set in &6%location%&7!";
        public String altarRemoved = "%prefix% &7Altar was successfully removed!";
        public String placedEye = "%prefix% &aSuccessfully placed ender eye &e%current%&6/&e%total%&a!";
        public String alreadyPlaced = "%prefix% &cThere is a ender eye placed in this altar!";
        public String errorEditor = "%prefix% &cYou can't do that in editor mode!";
        public String errorInProgress = "%prefix% &cYou can't do that, There is currently a dragon event in progress!";
        public String ritualCompleted = "%prefix% &aRitual has been completed, Starting Dragon Event!";
        public String newRecordMessage = "&d&l(NEW RECORD) ";
        public String playerNotFound = "none";
        public String bossBar = "%dragon_name% - &a%health%&c/%max_health% &c| Next Event in %remaining_time%";

        public String setupModeMessage = "&6► &6Type in the chat to set the new &6%type%&e! Type &cstop &eto exit from setup mode.";

        public List<String> setupModeJoin = Arrays.asList(
                "&e&m--------------"
                ,"&aYou're in editor mode"
                ," &e► &7Left-Click in a block to set as Altar."
                ," &e► &7Right-Click in a altar to remove."
                ,"&7Type command again to leave from editor mode."
                ,"&e&m--------------");

        public List<String> gameEndMessage = Arrays.asList(
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"
                ,""
                ,"                 &a%last_damager% &7He hit the dragon the last blow."
                ,""
                ,"                 &e&l1st Damager &7- %top_1% &7- &e%damage_1%"
                ,"                 &6&l2nd Damager &7- %top_2% &7- &e%damage_2%"
                ,"                 &c&l3rd Damager &7- %top_3% &7- &e%damage_3%"
                ,"                 "
                ,"                 &eYou Damage: &a%damage% %is_record%&7(Position #%rank%)"
                ,"                 &eRunecrafting Experience: &d%xp%"
                ,"                 "
                ,"&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
    }

    public static class GameConfigMessages extends OkaeriConfig{
        public boolean enabled = true;
        public String lightning = "%prefix% &aLightning Event has started, run away!";
        public String guardians = "%prefix% &aGuardians Event has started, prepare your swords!";
        public String fireball = "%prefix% &aFireball Event has started, run away!";
        public String dragonBall = "%prefix% &aDragon balls Event has started, run away!";
    }

    public static class GameTitlesMessages extends OkaeriConfig{
        public ConfigTitle dragonSpawning = new ConfigTitle("&5Dragon Spawning", "&6Spawning %time%");
        public ConfigTitle dragonSpawned = new ConfigTitle("&c%dragon%", "&7Spawned");
    }
}
