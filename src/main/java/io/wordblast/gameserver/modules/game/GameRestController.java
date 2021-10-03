package io.wordblast.gameserver.modules.game;

import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The game REST endpoints.
 */
@RestController
public class GameRestController {
    @Autowired
    private GameUtils gameUtils;

    /**
     * This endpoint will return an available game on the game server.
     * 
     * @return the available game.
     */
    @GetMapping("/api/game/available")
    public CompletableFuture<Game> getAvailableGame() {
        return gameUtils.getAvailableGameOrCreate();
    }
}
