package io.wordblast.gameserver.modules.game;

import io.wordblast.gameserver.modules.database.GameDao;
import java.util.UUID;
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
            game = new Game();

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
            .filter((game) -> game.getGameOptions().getVisibility() == GameVisibility.PUBLIC
                && game.getPlayers().size() < 8
                && game.getStatus() == GameStatus.WAITING)
            .findFirst()
            .orElse(null);
    }

    /**
     * Creates a new private game.
     * 
     * @param ownerUid the unique identifier of the owner user of the gmae.
     * @return the created game.
     */
    public CompletableFuture<Game> createPrivateGame(UUID ownerUid) {
        Game game = new Game();

        game.setOwner(ownerUid);
        game.getGameOptions().setVisibility(GameVisibility.PRIVATE);

        GameManager.registerGame(game);

        return gameDao.createGame(game);
    }

    /**
     * Attempts to retrieve an available game from the game manager. If none are found, create one.
     * 
     * @return the retrieved or created game.
     */
    public CompletableFuture<Game> createSinglePlayerGame() {
        final CompletableFuture<Game> gameFuture = new CompletableFuture<>();

        // Create a new game with limit on one player
        Game game = new Game();

        game.getGameOptions().setMaxPlayers(1);

        GameManager.registerGame(game);

        // Duplicate game variable and assign it the final modifier, so the value does not
        // change before the future is completed.
        final Game gameFinal = game;

        // Complete the game future once the game has been added to the database.
        gameDao.createGame(game).thenRun(() -> gameFuture.complete(gameFinal));
        return gameFuture;
    }
}
