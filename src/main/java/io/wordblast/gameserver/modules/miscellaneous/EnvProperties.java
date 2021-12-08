package io.wordblast.gameserver.modules.miscellaneous;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Extracts and exposes the environment properties from the application.properties file.
 */
@Configuration
public class EnvProperties {
    @Value("${SPRING_ENV:development}")
    private String env;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}
