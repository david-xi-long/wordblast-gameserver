package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * Packet representing an incoming message from game socket routes.
 */
public class PacketInGame extends Packet {
    private final UUID gameUid;
    private final UUID playerUid;

    /**
     * Creates a new incoing game packet instance.
     * 
     * @param gameUid the unique identifier of the game.
     * @param playerUid the unique identifier of the player.
     */
    public PacketInGame(PacketType type, UUID gameUid, UUID playerUid) {
        super(type);
        this.gameUid = gameUid;
        this.playerUid = playerUid;
    }

    public UUID getGameUid() {
        return gameUid;
    }

    public UUID getPlayerUid() {
        return playerUid;
    }
}
