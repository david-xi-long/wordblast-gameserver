package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * Packet representing an incoming start game request.
 */
public class PacketInStartGame extends Packet {
    private final UUID gameUid;
    private final String[] players;

    /**
     * Creates a new PacketInStartGame instance.
     * 
     * @param gameUid the unique identifier of the game.
     * @param players the players currently connected.
     */
    public PacketInStartGame(UUID gameUid, String[] players) {
        super(PacketType.PACKET_IN_START_GAME);
        this.gameUid = gameUid;
        this.players = players;
    }

    public UUID getGameUid() {
        return gameUid;
    }

    public String[] getPlayers() {
        return players;
    }
}
