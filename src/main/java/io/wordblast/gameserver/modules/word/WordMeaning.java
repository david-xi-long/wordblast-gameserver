package io.wordblast.gameserver.modules.word;

/**
 * A class describing the meaning of a word.
 */
public class WordMeaning {
    private String partOfSpeech;
    private WordDefinition[] definitions;

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public WordDefinition[] getDefinitions() {
        return definitions;
    }
}
