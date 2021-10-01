package io.wordblast.gameserver.modules.miscellaneous;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

/**
 * Extracts and exposes the environment properties from the application.properties file.
 */
@Configuration
@ConfigurationProperties(prefix = "env")
public class EnvProperties {
    private final String env;

    public EnvProperties() {
        this("DEV");
    }

    @ConstructorBinding
    public EnvProperties(final String env) {
        this.env = env;
    }

    public String getEnv() {
        return env;
    }
}
