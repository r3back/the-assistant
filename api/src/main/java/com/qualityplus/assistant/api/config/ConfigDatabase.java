package com.qualityplus.assistant.api.config;


import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Config for Database
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class ConfigDatabase extends OkaeriConfig {
    private String host = "localhost";
    private String database = "database_name";
    private String userName = "root";
    private String passWord = "123456";
    private int port = 8080;
    private boolean useSsl = false;
    private DatabaseType type = DatabaseType.H2;
}
