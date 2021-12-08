package io.wordblast.gameserver.modules.game.packets;

/**
 * Packet representing a word and a definition received from the dictionary API.
 */
public class PacketOutDefinition extends Packet {
    private final String word;
    private final String definition;

    /**
     * Creates a new PacketInGameWord.
     * 
     * @param word the word the player sent in through the game.
     * @param definition the definition received from the dictionary API for the corresponding word.
     */
    public PacketOutDefinition(String word, String definition) {
        super(PacketType.PACKET_OUT_DEFINITION);
        this.word = word;
        this.definition = definition;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }
}
