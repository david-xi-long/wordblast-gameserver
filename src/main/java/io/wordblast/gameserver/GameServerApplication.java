package io.wordblast.gameserver;

import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;

/**
 * The main class of the application.
 */
@SpringBootApplication(exclude = {
    MongoReactiveAutoConfiguration.class, MongoReactiveDataAutoConfiguration.class
})
public class GameServerApplication {
    /**
     * The entrypoint of the game server application.
     * 
     * @param args the arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(GameServerApplication.class);

        application.setDefaultProperties(Map.of("server.error.include-message", "always"));

        application.run(args);
    }
}
