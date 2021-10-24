package io.wordblast.gameserver.modules.word;

/**
 * A class describing the definition of a word.
 */
public class WordDefinition {
    private String definition;
    private String example;
    private String[] synonyms;
    private String[] antonyms;

    public String getDefinition() {
        return definition;
    }

    public String getExample() {
        return example;
    }

    public String[] getSynonyms() {
        return synonyms;
    }

    public String[] getAntonyms() {
        return antonyms;
    }
}
