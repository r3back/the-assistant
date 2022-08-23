package com.qualityplus.dragon;

import com.qualityplus.assistant.okaeri.OkaeriSilentPlugin;
import com.qualityplus.dragon.api.box.Box;
import com.qualityplus.dragon.persistance.data.UserData;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Scan;
import eu.okaeri.platform.core.plan.ExecutionPhase;
import eu.okaeri.platform.core.plan.Planned;
import lombok.Getter;
import com.qualityplus.dragon.api.TheDragonAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Scan(deep = true)
public final class TheDragon extends OkaeriSilentPlugin {
    private static @Inject @Getter TheDragonAPI api;

    @Planned(ExecutionPhase.PRE_SHUTDOWN)
    private void whenStop(Box box){
        Bukkit.getOnlinePlayers()
                .stream()
                .map(Player::getUniqueId)
                .forEach(uuid -> box.usersDB().getDragonData(uuid).ifPresent(UserData::save));

        box.game().finish();

        box.files().saveFiles();
    }
}