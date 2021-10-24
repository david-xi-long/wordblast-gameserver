package io.wordblast.gameserver.modules.word;

/**
 * A class representing the response from the dictionary API.
 */
public final class WordResponse {
    private String word;
    private String phonetic;
    private WordPhonetic[] phonetics;
    private String origin;
    private WordMeaning[] meanings;

    public String getWord() {
        return word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public WordPhonetic[] getPhonetics() {
        return phonetics;
    }

    public String getOrigin() {
        return origin;
    }

    public WordMeaning[] getMeanings() {
        return meanings;
    }
}
