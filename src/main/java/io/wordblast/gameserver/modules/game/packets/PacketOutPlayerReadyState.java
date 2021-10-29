package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * A packet sent to the frontend when a player has readied up.
 */
public class PacketOutPlayerReadyState extends Packet {
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
    public PacketOutPlayerReadyState(UUID gameUid, String username, boolean ready) {
        super(PacketType.PACKET_OUT_PLAYER_READY_STATE);
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
