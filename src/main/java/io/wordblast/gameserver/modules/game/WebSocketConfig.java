package io.wordblast.gameserver.modules.game;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Hooks;

/**
 * The game web socket configuration class.
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public void confiureSocket() {
        Hooks.onErrorDropped((err) -> {
        });
    }
}
