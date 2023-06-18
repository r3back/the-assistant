package com.qualityplus.assistant.listener;

import com.qualityplus.assistant.base.event.PlayerKillEvent;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Handles Player kill event
 */
@Component
public final class KillListener implements Listener {
    /**
     * Handles {@link PlayerKillEvent}
     *
     * @param e {@link EntityDeathEvent}
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDeath(final EntityDeathEvent e) {
        final Entity killer = e.getEntity().getKiller();

        if (killer == null) {
            return;
        }

        if (!(killer instanceof Player)) {
            return;
        }

        final Player player = e.getEntity().getKiller();

        final PlayerKillEvent event = new PlayerKillEvent(player, e.getEntity());

        Bukkit.getPluginManager().callEvent(event);
    }
}
