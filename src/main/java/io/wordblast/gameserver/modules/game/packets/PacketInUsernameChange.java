package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

// TODO: Modify PacketInGame so it only requires a game uid.
// TODO: Create PacketInPlayer which extends PacketInGame. This packet will have the player field.

/**
 * Packet representing a change username message incoming from game socket routes.
 */
public class PacketInUsernameChange extends Packet {
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
    public PacketInUsernameChange(UUID gameUid, String oldUsername, String newUsername) {
        super(PacketType.PACKET_IN_USERNAME_CHANGE);
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
