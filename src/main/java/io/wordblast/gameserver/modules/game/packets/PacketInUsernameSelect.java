package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * Packet representing a player selecting a username.
 */
public class PacketInUsernameSelect extends Packet {
    private final UUID gameUid;
    private final String username;

    /**
     * Creates a new PacketInUsernameSelect instance.
     * 
     * @param gameUid the unique identifier of the game.
     * @param username the username of the player.
     */
    public PacketInUsernameSelect(UUID gameUid, String username) {
        super(PacketType.PACKET_IN_USERNAME_SELECT);
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
