package io.wordblast.gameserver.modules.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Extracts and exposes the mongodb properties from the application.properties file.
 */
@Configuration
@ConfigurationProperties(prefix = "mongodb")
public class MongoProperties {
    private final String username;
    private final String password;

    public MongoProperties() {
        this(null, null);
    }

    public MongoProperties(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
