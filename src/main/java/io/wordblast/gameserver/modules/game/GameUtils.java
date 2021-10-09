package io.wordblast.gameserver.modules.game;

import io.wordblast.gameserver.modules.database.GameDao;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A utility class which contains various game related helper methods.
 */
@Service
public final class GameUtils {
    @Autowired
    private GameDao gameDao;

    /**
     * Class should not be externally instantiated.
     */
    private GameUtils() {}

    /**
     * Attempts to retrieve an available game from the game manager. If none are found, create one.
     * 
     * @return the retrieved or created game.
     */
    public CompletableFuture<Game> getAvailableGameOrCreate() {
        CompletableFuture<Game> gameFuture = new CompletableFuture<>();

        Game game = getAvailableGame();

        if (game != null) {
            gameFuture.complete(game);
        } else if (game == null) {
            game = createDefaultGame();

            GameManager.registerGame(game);

            // Duplicate game variable and assign it the final modifier, so the value does not
            // change before the future is completed.
            final Game gameFinal = game;

            // Complete the game future once the game has been added to the database.
            gameDao.createGame(game).thenRun(() -> gameFuture.complete(gameFinal));
        }

        return gameFuture;
    }

    /**
     * Attempts to retrieve an available game from the game manager.
     * 
     * @return the retrieved game if available, otherwise {@code null}.
     */
    public Game getAvailableGame() {
        return GameManager.getGames()
            .stream()
            .filter((game) -> game.getStatus() == GameStatus.WAITING
                && game.getPlayers().size() < 8)
            .findFirst()
            .orElse(null);
    }

    /**
     * Creates a game with the default settings.
     * 
     * @return the created game.
     */
    public Game createDefaultGame() {
        return new Game();
    }
}
