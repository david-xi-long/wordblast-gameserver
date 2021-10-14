package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 *  Packet representing the frontend requesting to start the game.
 */
public class PacketOutStartGame extends Packet {
    private final UUID gameUid;
    private final String[] players;

    /**
     * Crates a new PacketOutChangeUsername instance.
     * 
     * @param gameUid the unique identifier of the game.
     * @param players the players at the start of the game.
     */
    public PacketOutStartGame(UUID gameUid, String[] players) {
        super(PacketType.PACKET_OUT_START_GAME);
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
