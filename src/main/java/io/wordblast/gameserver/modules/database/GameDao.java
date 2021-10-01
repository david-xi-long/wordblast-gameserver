package io.wordblast.gameserver.modules.database;

import io.wordblast.gameserver.modules.game.Game;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The game data access object.
 */
@Service
public class GameDao {
    @Autowired
    public GameRepository gameRepository;

    public CompletableFuture<Game> createGame(Game game) {
        return gameRepository.insert(game).toFuture();
    }
}
