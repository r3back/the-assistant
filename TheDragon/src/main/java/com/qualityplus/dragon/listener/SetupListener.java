package com.qualityplus.dragon.listener;

import com.qualityplus.assistant.util.StringUtils;
import com.qualityplus.dragon.api.box.Box;
import com.qualityplus.dragon.api.service.SetupService;
import com.qualityplus.dragon.base.service.SetupServiceImpl;
import eu.okaeri.injector.annotation.Inject;
import com.qualityplus.dragon.base.game.guardian.DragonGuardian;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Handles the Setup
 */
public final class SetupListener implements Listener {
    private @Inject SetupService setupService;
    private @Inject Box box;

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        Set<UUID> editorMode = setupService.getPlayersInSetupMode();

        if(!editorMode.contains(uuid)) return;

        if(setupService.manageDragonAltars(player, e)) e.setCancelled(true);
    }

    @EventHandler
    public void checkChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        SetupService setupManager = setupService;
        Map<DragonGuardian, SetupServiceImpl.EditType> setupMode = setupManager.get(uuid);
        if (setupMode != null && !setupMode.isEmpty()) {
            Optional<DragonGuardian> key = setupMode.keySet().stream().findFirst();
            SetupServiceImpl.EditType edit = setupMode.get(key.get());
            event.setCancelled(true);
            String message = event.getMessage();
            DragonGuardian ultimateItem = key.get();
            if(message.contains("leave") || message.contains("exit") || message.contains("stop")){
                setupManager.openGUIAsync(player, ultimateItem);
            }else{
                try{
                    if (edit == SetupServiceImpl.EditType.HEALTH) {
                        double manaCost = Double.parseDouble(message);
                        ultimateItem.setHealth(manaCost);
                    }else if(edit == SetupServiceImpl.EditType.MOB) {
                        EntityType entityType = EntityType.valueOf(message);
                        entityType.name();
                        ultimateItem.setEntity(message);
                    }else if(edit == SetupServiceImpl.EditType.DISPLAYNAME)
                        ultimateItem.setDisplayName(message);
                    setupManager.remove(uuid);
                    setupManager.openGUIAsync(player, ultimateItem);
                } catch (NumberFormatException e) {
                    player.sendMessage(StringUtils.color(box.files().messages().pluginMessages.invalidAmount
                            .replace("%prefix%", box.files().config().prefix)));
                } catch (IllegalArgumentException e){
                    player.sendMessage(StringUtils.color("%prefix% &cInvalid Mob!"
                            .replace("%prefix%", box.files().config().prefix)));
                }
            }
        }
    }
}
