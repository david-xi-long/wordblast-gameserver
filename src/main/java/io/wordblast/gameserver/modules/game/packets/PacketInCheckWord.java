package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * Packet representing a word being checked.
 */
public class PacketInCheckWord extends Packet {
    private final String word;
    private final UUID gameUid;

    /**
     * Creates a new PacketInCheckWord instance.
     * 
     * @param word the entered input from the player.
     */
    public PacketInCheckWord(String word, UUID gameUid) {
        super(PacketType.PACKET_IN_CHECK_WORD);
        this.word = word;
        this.gameUid = gameUid;
    }

    public String getWord() {
        return word;
    }

    public UUID getGameUid() {
        return gameUid;
    }
}