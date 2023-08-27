package com.qualityplus.assistant.api.nms.tab;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Runnable Task for Tab Handler
 */
public class TabRunnable extends BukkitRunnable {
    private final TabHandler handler;

    /**
     * Constructor to make a new TabThread
     *
     * @param handler the handler to register it to
     */
    public TabRunnable(final TabHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.handler.sendUpdate(player);
        }
    }
}
