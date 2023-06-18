package com.qualityplus.assistant.api.database;

import com.qualityplus.assistant.api.config.ConfigDatabase;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * Database url factory
 */
@NoArgsConstructor
public abstract class DBUrlFactory {
    private static final String EMPTY_STRING = "";

    /**
     * Retrieves parsed database url
     *
     * @param configDatabase {@link ConfigDatabase}
     * @return parsed uri
     */
    public String parseDatabaseUrl(final ConfigDatabase configDatabase) {
        return configDatabase.getType().getUri()
                .replaceAll("user", getOrEmpty(configDatabase.getUserName()))
                .replaceAll("host", getOrEmpty(configDatabase.getHost()))
                .replaceAll("port", getOrEmpty(String.valueOf(configDatabase.getPort())))
                .replaceAll("password", getOrEmpty(configDatabase.getPassWord()))
                .replaceAll("database", getOrEmpty(configDatabase.getDatabase()));
    }

    private String getOrEmpty(final String value) {
        return Optional.ofNullable(value).orElse(EMPTY_STRING);
    }
}
