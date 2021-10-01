package io.wordblast.gameserver.modules.game;

import java.util.UUID;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The game resource.
 */
@Document(collection = "games")
public class Game {
    private final UUID uuid;
    private GameStatus status;

    public Game() {
        this(UUID.randomUUID());
    }

    public Game(final UUID uuid) {
        this.uuid = uuid;
        this.status = GameStatus.WAITING;
    }

    public UUID getUuid() {
        return uuid;
    }

    public GameStatus getStatus() {
        return status;
    }
}
