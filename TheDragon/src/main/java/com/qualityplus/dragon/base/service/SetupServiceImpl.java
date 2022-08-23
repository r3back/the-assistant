package com.qualityplus.dragon.base.service;

import com.qualityplus.assistant.util.StringUtils;
import com.qualityplus.assistant.util.location.LocationUtils;
import com.qualityplus.dragon.api.box.Box;
import com.qualityplus.dragon.api.service.SetupService;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.qualityplus.dragon.TheDragon;
import com.qualityplus.dragon.api.game.DragonGame;
import com.qualityplus.dragon.api.game.structure.type.DragonAltar;
import com.qualityplus.dragon.base.game.structure.DragonAltarImpl;
import com.qualityplus.dragon.gui.guardian.GuardianGUI;
import com.qualityplus.dragon.base.game.guardian.DragonGuardian;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.*;

/**
 * Handles the Editor Mode Data
 */
@Component
public final class SetupServiceImpl implements SetupService {
    private final Map<UUID, Map<DragonGuardian, EditType>> editSettings = new HashMap<>();
    private @Getter final Set<UUID> editorMode = new HashSet<>();
    private @Inject Box box;

    @Override
    public Set<UUID> getPlayersInSetupMode() {
        return editorMode;
    }

    @Override
    public void openGUIAsync(Player player, DragonGuardian guardian){
        Bukkit.getScheduler().scheduleSyncDelayedTask(TheDragon.getApi().getPlugin(), () -> player.openInventory(new GuardianGUI(box, guardian).getInventory()),3L);
    }


    @Override
    public boolean manageDragonAltars(Player player, PlayerInteractEvent e){
        Action action = e.getAction();

        if(action != Action.RIGHT_CLICK_BLOCK && action != Action.LEFT_CLICK_BLOCK) return false;

        DragonGame dragonGame = box.game();

        Location location = e.getClickedBlock().getLocation();

        Optional<DragonAltar> altar = box.structures().getAltar(location);

        if(altar.isPresent() && action == Action.RIGHT_CLICK_BLOCK){
            box.structures().removeStructure(altar.get());
            player.sendMessage(StringUtils.color(box.files().messages().setupMessages.altarRemoved.replace("%prefix%", box.files().config().prefix)));
        }else if(!altar.isPresent()){
            if(e.getAction() != Action.LEFT_CLICK_BLOCK) return true;
            box.structures().addStructure(new DragonAltarImpl(location, false));
            player.sendMessage(StringUtils.color(box.files().messages().setupMessages.altarSet
                    .replace("%location%", LocationUtils.toString(location))
                    .replace("%prefix%", box.files().config().prefix)));
        }
        return true;
    }

    public Map<DragonGuardian, EditType> get(UUID uuid){
        return editSettings.getOrDefault(uuid, null);
    }

    @Override
    public void setInSetupMode(UUID uuid, DragonGuardian key, EditType editType){
        editSettings.put(uuid, new HashMap<DragonGuardian, EditType>(){{
            put(key, editType);
        }});
    }

    public void remove(UUID uuid){
        editSettings.remove(uuid);
    }

    public @AllArgsConstructor enum EditType{
        HEALTH("health"),
        MOB("mob type"),
        DISPLAYNAME("displayname");

        private @Getter final String name;
    }
}
