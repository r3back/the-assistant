package com.qualityplus.assistant.api.database;

import com.qualityplus.assistant.api.config.ConfigDatabase;
import com.zaxxer.hikari.HikariConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom Hikari pool configurations
 */
public abstract class HikariConfiguration extends DBUrlFactory {
    private static final String H2_URL = "jdbc:h2:file:./plugins/%s/storage/storage;MODE=MYSQL;DATABASE_TO_LOWER=TRUE";
    private static final String MYSQL_URL = "jdbc:mysql://%s:%s/%s?useSSL=%s";

    protected HikariConfig getMysqlHikari(final ConfigDatabase db) {
        final HikariConfig mysqlHikari = new HikariConfig();

        mysqlHikari.setDriverClassName("com.mysql.cj.jdbc.Driver");

        final String mysqlUrl = String.format(MYSQL_URL, db.getHost(), db.getPort(), db.getDatabase(), db.isUseSsl());

        mysqlHikari.setJdbcUrl(mysqlUrl);

        mysqlHikari.setUsername(db.getUserName());
        mysqlHikari.setPassword(db.getPassWord());

        final Map<String, String> properties = new HashMap<>();
        properties.putIfAbsent("cachePrepStmts", "true");
        properties.putIfAbsent("prepStmtCacheSize", "250");
        properties.putIfAbsent("prepStmtCacheSqlLimit", "2048");
        properties.putIfAbsent("useServerPrepStmts", "true");
        properties.putIfAbsent("useLocalSessionState", "true");
        properties.putIfAbsent("rewriteBatchedStatements", "true");
        properties.putIfAbsent("cacheResultSetMetadata", "true");
        properties.putIfAbsent("cacheServerConfiguration", "true");
        properties.putIfAbsent("elideSetAutoCommits", "true");
        properties.putIfAbsent("maintainTimeStats", "false");
        properties.putIfAbsent("alwaysSendSetIsolation", "false");
        properties.putIfAbsent("cacheCallableStmts", "true");

        for (final Map.Entry<String, String> property : properties.entrySet()) {
            mysqlHikari.addDataSourceProperty(property.getKey(), property.getValue());
        }

        return mysqlHikari;
    }

    protected HikariConfig getMariaDBHikari(final ConfigDatabase db) {
        final HikariConfig mariadbHikari = new HikariConfig();

        mariadbHikari.setDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
        mariadbHikari.addDataSourceProperty("serverName", db.getHost());
        mariadbHikari.addDataSourceProperty("port", db.getPort());
        mariadbHikari.addDataSourceProperty("databaseName", db.getDatabase());
        mariadbHikari.setUsername(db.getUserName());
        mariadbHikari.setPassword(db.getPassWord());

        return mariadbHikari;
    }

    protected HikariConfig getH2Hikari(final String name) {
        final HikariConfig jdbcHikari = new HikariConfig();

        jdbcHikari.setJdbcUrl(String.format(H2_URL, name));

        return jdbcHikari;
    }
}
