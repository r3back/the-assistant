package com.qualityplus.dragon.api.factory;

import com.qualityplus.dragon.api.handler.ReplyHandler;
import org.bukkit.entity.Player;

/**
 * Boss Bar Driver Factory
 */
public interface BossBarFactory {
    /**
     *
     * @return Boss Bar Reply
     */
    ReplyHandler<Player, String> getBossBar();
}
