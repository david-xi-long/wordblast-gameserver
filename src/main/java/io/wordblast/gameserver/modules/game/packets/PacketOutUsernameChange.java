package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * Packet which is echoed to a socket client if their username change was successful.
 */
public class PacketOutUsernameChange extends Packet {
    private final UUID gameUid;
    private final String oldUsername;
    private final String newUsername;

    /**
     * Creates a new PacketInChangeUsername instance.
     * 
     * @param gameUid the unique identifier of the game.
     * @param oldUsername the old username of the player.
     * @param newUsername the new username of the player.
     */
    public PacketOutUsernameChange(UUID gameUid, String oldUsername, String newUsername) {
        super(PacketType.PACKET_OUT_USERNAME_CHANGE);
        this.gameUid = gameUid;
        this.oldUsername = oldUsername;
        this.newUsername = newUsername;
    }

    public UUID getGameUid() {
        return gameUid;
    }

    public String getOldUsername() {
        return oldUsername;
    }

    public String getNewUsername() {
        return newUsername;
    }
}
