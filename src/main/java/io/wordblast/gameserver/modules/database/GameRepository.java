package io.wordblast.gameserver.modules.database;

import io.wordblast.gameserver.modules.game.Game;
import java.util.UUID;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface describing the game repository.
 */
@Repository
public interface GameRepository extends ReactiveMongoRepository<Game, UUID> {
}
