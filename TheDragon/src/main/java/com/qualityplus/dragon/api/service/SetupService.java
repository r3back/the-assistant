package com.qualityplus.dragon.api.service;

import com.qualityplus.dragon.base.game.guardian.DragonGuardian;
import com.qualityplus.dragon.base.service.SetupServiceImpl;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface SetupService {
    Set<UUID> getPlayersInSetupMode();
    void openGUIAsync(Player player, DragonGuardian guardian);
    boolean manageDragonAltars(Player player, PlayerInteractEvent e);
    void setInSetupMode(UUID player, DragonGuardian guardian, SetupServiceImpl.EditType editType);
    Map<DragonGuardian, SetupServiceImpl.EditType> get(UUID uuid);
    void remove(UUID uuid);
}
