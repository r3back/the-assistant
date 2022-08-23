package com.qualityplus.dragon.base.factory;

import com.qualityplus.assistant.config.ConfigDatabase;
import com.qualityplus.assistant.config.database.DatabaseType;
import com.qualityplus.dragon.base.configs.Config;
import com.zaxxer.hikari.HikariConfig;
import eu.okaeri.configs.json.simple.JsonSimpleConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.persistence.PersistencePath;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.jdbc.H2Persistence;
import eu.okaeri.persistence.jdbc.MariaDbPersistence;
import eu.okaeri.platform.bukkit.persistence.YamlBukkitPersistence;
import eu.okaeri.platform.core.annotation.Bean;
import eu.okaeri.platform.core.annotation.Component;

import java.io.File;

@Component
public final class DatabaseFactory {
    @Bean(value = "persistence", preload = true)
    public DocumentPersistence configurePersistence(@Inject("dataFolder") File dataFolder, @Inject Config config) {

        try {Class.forName("org.mariadb.jdbc.Driver");} catch (Exception e) { e.printStackTrace();}
        try {Class.forName("org.h2.Driver");} catch (Exception e) { e.printStackTrace();}
        try {Class.forName("com.mysql");} catch (Exception ignored) { }

        PersistencePath basePath = PersistencePath.of("dragonDb");

        ConfigDatabase db = config.configDatabase;

        DatabaseType backend = db.type;

        String h2Uri = "jdbc:h2:file:./plugins/TheDragon/storage/storage;MODE=MYSQL;DATABASE_TO_LOWER=TRUE";

        switch (backend) {
            case FLAT:
                return YamlBukkitPersistence.of(new File(dataFolder, "storage"));
            case MYSQL:
                HikariConfig mariadbHikari = new HikariConfig();

                mariadbHikari.setJdbcUrl("jdbc:mysql://" + db.host + ":" + db.port + "/" + db.database);

                mariadbHikari.setUsername(config.configDatabase.userName);
                mariadbHikari.setPassword(config.configDatabase.passWord);

                return new DocumentPersistence(new MariaDbPersistence(basePath, mariadbHikari), JsonSimpleConfigurer::new, new SerdesBukkit());
            case H2:
                HikariConfig jdbcHikari = new HikariConfig();

                jdbcHikari.setJdbcUrl(h2Uri);

                return new DocumentPersistence(new H2Persistence(basePath, jdbcHikari), JsonSimpleConfigurer::new, new SerdesBukkit());
            default:
                throw new RuntimeException("unsupported storage backend: " + backend);
        }
    }
}