package com.qualityplus.assistant.api.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Database types representation
 */
@RequiredArgsConstructor
public enum DatabaseType {
    /**
     * Maria DB Database
     */
    MARIADB("jdbc:mysql://%host%:%port%/%database%"),
    /**
     * MySQL Database
     */
    MYSQL("jdbc:mysql://%host%:%port%/%database%"),
    /**
     * H2 Database
     */
    H2(""),
    /**
     * Flat YAML Database
     */
    FLAT(""),
    /**
     * Redis Database
     */
    REDIS("redis://%user%:%password%@%host%:%port%/0"),
    /**
     * MongoDB Factory
     */
    MONGODB("mongodb://%user%:%password%@%host%:%port%/db");

    @Getter
    private final String uri;
}
