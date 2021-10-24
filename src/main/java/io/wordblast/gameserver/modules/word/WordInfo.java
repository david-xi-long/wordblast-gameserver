package io.wordblast.gameserver.modules.word;

/**
 * A class which provides the information of a word.
 */
public class WordInfo {
    private final String word;
    private final String type;
    private final String definition;

    /**
     * Creates a new WordInfo object.
     * 
     * @param word the word of the information object.
     * @param type the type of the word.
     * @param definition the definition of the wor.d
     */
    public WordInfo(String word, String type, String definition) {
        this.word = word;
        this.type = type;
        this.definition = definition;
    }

    public String getWord() {
        return word;
    }

    public String getType() {
        return type;
    }

    public String getDefinition() {
        return definition;
    }
}
