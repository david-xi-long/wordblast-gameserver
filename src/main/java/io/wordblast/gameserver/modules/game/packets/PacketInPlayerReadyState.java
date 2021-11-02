package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * A packet sent to the game server when a player has readied up.
 */
public class PacketInPlayerReadyState extends Packet implements GamePacket, PlayerPacket {
    private final UUID gameUid;
    private final String username;
    private final boolean ready;

    /**
     * Creates a new PacketInPlayerReady instance.
     * 
     * @param gameUid the unique identifier of the game the player is in.
     * @param username the username of the player.
     * @param ready whether the player is ready or not.
     */
    public PacketInPlayerReadyState(UUID gameUid, String username, boolean ready) {
        super(PacketType.PACKET_IN_PLAYER_READY_STATE);
        this.gameUid = gameUid;
        this.username = username;
        this.ready = ready;
    }

    public UUID getGameUid() {
        return gameUid;
    }

    public String getUsername() {
        return username;
    }

    public boolean isReady() {
        return ready;
    }
}
