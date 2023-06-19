package com.qualityplus.assistant.driver;

import eu.okaeri.commons.bukkit.time.MinecraftTimeEquivalent;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.persistence.jdbc.H2Persistence;
import eu.okaeri.persistence.jdbc.MariaDbPersistence;
import eu.okaeri.persistence.mongo.MongoPersistence;
import eu.okaeri.persistence.redis.RedisPersistence;
import eu.okaeri.platform.bukkit.annotation.Delayed;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

/**
 * Class to load database drivers
 *
 * TODO check if this can be removed
 */
@Component
public final class DriverLoader {
    /**
     * Load database drivers
     *
     * @param plugin {@link Plugin}
     * @param logger {@link Logger}
     */
    @Delayed(time = MinecraftTimeEquivalent.SECOND / 20, async = true)
    public void loadDrivers(final @Inject Plugin plugin, final @Inject Logger logger) {
        try {

            MariaDbPersistence.class.getName();
            H2Persistence.class.getName();
            MongoPersistence.class.getName();
            RedisPersistence.class.getName();

            logger.info("Successfully loaded Database Drivers");
        } catch (final Exception ignored) {
            logger.info("Fail while loading Database Drivers!");
            Bukkit.getServer().getPluginManager().disablePlugin(plugin);
        }
    }
}
