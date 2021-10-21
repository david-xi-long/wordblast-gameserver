package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * Packet representing a single character that a player may type
 * as they try to submit a valid word while in-game
 */
public class PacketInCharacter extends Packet {
    private final UUID gameUid;
    private final String username;
    private final char character;

    /**
     * Creates a new PacketInCharacter
     * 
     * @param gameUid the unique identifier of the game.
     * @param username the username of the player inputting the character
     * @param message the character the player sent in through the game
     */
    public PacketInCharacter(UUID gameUid, String username, char character) {
        super(PacketType.PACKET_IN_CHARACTER);
        this.gameUid = gameUid;
        this.username = username;
        this.character = character;
    }

    public UUID getGameUid() {
        return gameUid;
    }

    public String getUsername() {
        return username;
    }

    public char getCharacter() {
        return character;
    }
}
