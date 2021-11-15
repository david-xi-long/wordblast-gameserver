package io.wordblast.gameserver.modules.game;

import io.wordblast.gameserver.modules.authentication.NotAuthorizedException;
import io.wordblast.gameserver.modules.authentication.UserDetailsImpl;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
     * Attempts to create a new private game.
     * 
     * @param user the owner of the new game.
     * @return the game, if created.
     */
    @PostMapping("/api/game")
    public CompletableFuture<Game> createPrivateGame(
        @AuthenticationPrincipal UserDetailsImpl user) {
        if (user == null) {
            return CompletableFuture.failedFuture(new NotAuthorizedException());
        }

        return gameUtils.createPrivateGame(user.getUid());
    }

    /**
     * This endpoint will create and return a new single player game.
     * 
     * @return the newly created game.
     */
    @GetMapping("/api/game/singlePlayer")
    public CompletableFuture<Game> getSinglePlayerGame() {
        return gameUtils.createSinglePlayerGame();
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

    /**
     * This endpoint will return the number of players which are currently inside a game.
     * 
     * @return the count of players.
     */
    @GetMapping("/api/game/count")
    public Mono<String> getPlayingCount() {
        return Mono.just(
            String.format("{\"count\": %d}",
                GameManager.getGames()
                    .stream()
                    .flatMap((g) -> g.getPlayers()
                        .stream()
                        .filter((p) -> p.getState() == PlayerState.ACTIVE))
                    .count()));
    }

    /**
     * Handles exceptions thrown inside request handlers.
     * 
     * @param ex the exception to handle.
     * @return the result of the handled exception.
     */
    @ExceptionHandler
    public ResponseEntity<String> handleExceptions(Exception ex) {
        HttpStatus status;
        String exMessage;

        if (ex instanceof NotAuthorizedException) {
            status = HttpStatus.UNAUTHORIZED;
            exMessage = ex.getMessage();
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            exMessage = ex.getMessage();
        }

        String errMessage = String.format("{\"error\": \"%s\"}", exMessage);

        return new ResponseEntity<>(errMessage, status);
    }
}
