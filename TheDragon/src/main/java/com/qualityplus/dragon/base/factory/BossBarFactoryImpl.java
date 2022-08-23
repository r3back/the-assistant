package com.qualityplus.dragon.base.factory;

import com.cryptomorin.xseries.XMaterial;
import com.qualityplus.dragon.api.factory.BossBarFactory;
import com.qualityplus.dragon.api.handler.ReplyHandler;
import com.qualityplus.dragon.base.handler.BossBarHandler;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.entity.Player;

@Component
public final class BossBarFactoryImpl implements BossBarFactory {
    private ReplyHandler<Player, String> bossBar;

    @Override
    public ReplyHandler<Player, String> getBossBar() {
        /*
        TODO
         */
        //return bossBar == null ? bossBar = XMaterial.getVersion() < 10 ? new BossBarHandler_1_8_R3():  new BossBarHandler() : bossBar;

        return bossBar == null ? new BossBarHandler() : bossBar;
    }
}
