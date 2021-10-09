package io.wordblast.gameserver.modules.game;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * This endpoint will attempt to return the requested game.
     * 
     * @param gameUid the unique identifier of the requested game.
     * @return the retrieved game.
     */
    @GetMapping("/api/game/{gameUid}")
    public ResponseEntity<Game> getGame(@PathVariable UUID gameUid) {
        Game game = GameManager.getGame(gameUid);
        return new ResponseEntity<>(game, game != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
