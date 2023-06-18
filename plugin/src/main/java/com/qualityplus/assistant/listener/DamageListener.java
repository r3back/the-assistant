package com.qualityplus.assistant.listener;

import com.qualityplus.assistant.base.event.EntityDamagedByPlayerEvent;
import com.qualityplus.assistant.base.event.PlayerDamagedByEntityEvent;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Damage listener
 */
@Component
public final class DamageListener implements Listener {
    /**
     * Handles {@link EntityDamagedByPlayerEvent}
     *
     * @param e {@link EntityDamageByEntityEvent}
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void entityDamagedByPlayerEvent(final EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }

        final EntityDamagedByPlayerEvent event = new EntityDamagedByPlayerEvent(
                (Player) e.getDamager(), e.getEntity()
        );

        Bukkit.getPluginManager().callEvent(event);
    }

    /**
     * Handles {@link PlayerDamagedByEntityEvent}
     *
     * @param e {@link EntityDamageByEntityEvent}
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void playerDamagedByEntityEvent(final EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        final PlayerDamagedByEntityEvent event = new PlayerDamagedByEntityEvent(
                (Player) e.getEntity(), e.getDamager()
        );

        Bukkit.getPluginManager().callEvent(event);
    }
}
