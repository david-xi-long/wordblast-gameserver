package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * Packet representing a single character that a player may type as they try to submit a valid word
 * while in-game.
 */
public class PacketInGameWord extends Packet {
    private final UUID gameUid;
    private final String username;
    private final String word;

    /**
     * Creates a new PacketInCharacter.
     * 
     * @param gameUid the unique identifier of the game.
     * @param username the username of the player inputting the character.
     * @param word the character the player sent in through the game.
     */
    public PacketInGameWord(UUID gameUid, String username, String word) {
        super(PacketType.PACKET_IN_GAME_WORD);
        this.gameUid = gameUid;
        this.username = username;
        this.word = word;
    }

    public UUID getGameUid() {
        return gameUid;
    }

    public String getUsername() {
        return username;
    }

    public char getWord() {
        return word;
    }
}
