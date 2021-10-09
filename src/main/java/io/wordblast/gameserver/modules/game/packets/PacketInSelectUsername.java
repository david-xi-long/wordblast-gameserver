package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * Packet representing a player selecting a username.
 */
public class PacketInSelectUsername extends Packet {
    private final UUID gameUid;
    private final String username;

    /**
     * Creates a new PacketInSelectUsername instance.
     * 
     * @param gameUid the unique identifier of the game.
     * @param username the username of the player.
     */
    public PacketInSelectUsername(UUID gameUid, String username) {
        super(PacketType.PACKET_IN_SELECT_USERNAME);
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
