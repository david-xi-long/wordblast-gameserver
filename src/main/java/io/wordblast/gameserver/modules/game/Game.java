package io.wordblast.gameserver.modules.game;

import java.util.UUID;

/**
 * The game resource.
 */
public class Game {
    private UUID uuid;

    public Game(final UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
