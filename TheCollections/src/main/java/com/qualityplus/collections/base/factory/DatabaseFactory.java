package com.qualityplus.collections.base.factory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.qualityplus.assistant.base.factory.UriGetter;
import com.qualityplus.assistant.config.ConfigDatabase;
import com.qualityplus.assistant.config.database.DatabaseType;
import com.qualityplus.collections.base.config.Config;
import com.zaxxer.hikari.HikariConfig;
import eu.okaeri.configs.json.simple.JsonSimpleConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.persistence.PersistencePath;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.jdbc.H2Persistence;
import eu.okaeri.persistence.jdbc.MariaDbPersistence;
import eu.okaeri.persistence.mongo.MongoPersistence;
import eu.okaeri.persistence.redis.RedisPersistence;
import eu.okaeri.platform.bukkit.persistence.YamlBukkitPersistence;
import eu.okaeri.platform.core.annotation.Bean;
import eu.okaeri.platform.core.annotation.Component;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;

import java.io.File;

@Component
public final class DatabaseFactory implements UriGetter {
    @Bean(value = "persistence", preload = true)
    public DocumentPersistence configurePersistence(@Inject("dataFolder") File dataFolder, @Inject Config config) {

        try { Class.forName("org.mariadb.jdbc.Driver"); } catch (ClassNotFoundException ignored) { }
        try { Class.forName("org.h2.Driver"); } catch (ClassNotFoundException ignored) { }
        try { Class.forName("com.mysql"); } catch (ClassNotFoundException ignored) { }

        PersistencePath basePath = PersistencePath.of("collectionsDb");

        ConfigDatabase db = config.getConfigDatabase();

        DatabaseType backend = db.type;

        String h2Uri = "jdbc:h2:file:./plugins/TheCollections/storage/storage;MODE=MYSQL;DATABASE_TO_LOWER=TRUE";

        switch (backend) {
            case FLAT:
                return YamlBukkitPersistence.of(new File(dataFolder, "storage"));
            case MYSQL:
                HikariConfig mariadbHikari = new HikariConfig();

                mariadbHikari.setJdbcUrl("jdbc:mysql://" + db.host + ":" + db.port + "/" + db.database);

                mariadbHikari.setUsername(config.getConfigDatabase().userName);
                mariadbHikari.setPassword(config.getConfigDatabase().passWord);

                return new DocumentPersistence(new MariaDbPersistence(basePath, mariadbHikari), JsonSimpleConfigurer::new, new SerdesBukkit());
            case H2:
                HikariConfig jdbcHikari = new HikariConfig();

                jdbcHikari.setJdbcUrl(h2Uri);

                return new DocumentPersistence(new H2Persistence(basePath, jdbcHikari), JsonSimpleConfigurer::new, new SerdesBukkit());
            case REDIS:
                RedisURI redisUri = RedisURI.create(getUri(db));

                RedisClient redisClient = RedisClient.create(redisUri);

                return new DocumentPersistence(new RedisPersistence(basePath, redisClient), JsonSimpleConfigurer::new, new SerdesBukkit());
            case MONGODB:
                MongoClientURI clientURI = new MongoClientURI(getUri(db));

                MongoClient mongoClient = new MongoClient(clientURI);

                if (clientURI.getDatabase() == null)
                    throw new IllegalArgumentException("Mongo URI needs to specify the database");

                return new DocumentPersistence(new MongoPersistence(basePath, mongoClient, clientURI.getDatabase()), JsonSimpleConfigurer::new, new SerdesBukkit());
            default:
                throw new RuntimeException("unsupported storage backend: " + backend);
        }
    }
}
