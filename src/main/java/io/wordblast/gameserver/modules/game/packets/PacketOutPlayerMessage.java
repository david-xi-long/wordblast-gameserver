package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * Packet representing a player selecting a username.
 */
public class PacketOutPlayerMessage extends Packet {
    private final UUID gameUid;
    private final String username;
    private final String message;

    /**
     * Creates a new PacketInGameChatMessage instance.
     * 
     * @param gameUid the unique identifier of the game.
     * @param username the username of the player sending the message
     * @param message the message content
     */
    public PacketOutPlayerMessage(UUID gameUid, String username, String message) {
        super(PacketType.PACKET_OUT_PLAYER_MESSAGE);
        this.gameUid = gameUid;
        this.username = username;
        this.message = message;
    }

    public UUID getGameUid() {
        return gameUid;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
