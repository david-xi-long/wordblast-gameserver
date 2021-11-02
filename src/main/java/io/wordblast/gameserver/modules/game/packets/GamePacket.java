package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * A packet which contains a game uid getter.
 */
public interface GamePacket {
    public UUID getGameUid();
}
