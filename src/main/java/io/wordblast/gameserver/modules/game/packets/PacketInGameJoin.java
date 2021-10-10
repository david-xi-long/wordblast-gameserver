package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * Packet representing a join game message incoming from game socket routes.
 */
public final class PacketInGameJoin extends Packet {
    private final UUID gameUid;
    private final String username;

    /**
     * Creates a new PacketInGameJoin instance.
     * 
     * @param gameUid the unique identifier of the game.
     * @param username the username of the player.
     */
    public PacketInGameJoin(UUID gameUid, String username) {
        super(PacketType.PACKET_IN_GAME_JOIN);
        this.gameUid = gameUid;
        this.username = username;
    }

    public UUID getGameUid() {
        return gameUid;
    }

    public String getUsername() {
        return username;
    }
}
