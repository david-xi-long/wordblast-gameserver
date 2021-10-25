package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * Packet representing a word as the player attempts to come up with a valid word
 * This is NOT a word submission - this is so that other players in the game can watch
 * how the player comes up with a word
 * while in-game.
 */
public class PacketOutGameWord extends Packet {
    private final UUID gameUid;
    private final String username;
    private final String word;

    /**
     * Creates a new PacketInGameWord
     * 
     * @param gameUid the unique identifier of the game.
     * @param username the username of the player inputting the character.
     * @param word the character the player sent in through the game.
     */
    public PacketOutGameWord(UUID gameUid, String username, String word) {
        super(PacketType.PACKET_OUT_GAME_WORD);
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

    public String getWord() {
        return word;
    }
}
