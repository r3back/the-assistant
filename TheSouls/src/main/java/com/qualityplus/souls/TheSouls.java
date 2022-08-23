package com.qualityplus.souls;

import com.qualityplus.assistant.okaeri.OkaeriSilentPlugin;
import com.qualityplus.souls.api.TheSoulsAPI;
import com.qualityplus.souls.api.box.Box;
import com.qualityplus.souls.base.soul.Soul;
import com.qualityplus.souls.persistance.data.SoulsData;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Scan;
import eu.okaeri.platform.core.plan.ExecutionPhase;
import eu.okaeri.platform.core.plan.Planned;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Scan(deep = true)
public final class TheSouls extends OkaeriSilentPlugin {
    private static @Inject @Getter TheSoulsAPI api;

    @Planned(ExecutionPhase.PRE_SHUTDOWN)
    private void whenStop(@Inject Box box) {
        box.files().souls().soulList.forEach(Soul::disable);
        box.files().souls().save();

        Bukkit.getOnlinePlayers()
                .stream()
                .map(Player::getUniqueId)
                .forEach(uuid -> box.service().getData(uuid).ifPresent(SoulsData::save));
    }

    @Planned(ExecutionPhase.POST_STARTUP)
    private void whenStart(@Inject Box box) {
        box.files().souls().soulList.forEach(soul -> soul.enable(box));
    }
}
