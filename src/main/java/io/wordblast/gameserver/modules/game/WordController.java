package io.wordblast.gameserver.modules.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A class which generates and provides letter combinations.
 */
public class WordController {
    private final Set<String> words = new HashSet<>();
    private final Map<Integer, List<String>> letterCombinations = new HashMap<>();

    /**
     * Sets the words of the controller.
     * 
     * @param words the words of the controller.
     */
    public void setWords(Set<String> words) {
        this.words.clear();
        this.words.addAll(words);

        addWordCombinations(words);
    }

    /**
     * Retrieves a random combination of the specified length.
     * 
     * @param length the length of the combination.
     * @return the retrieved combination.
     */
    public String getRandomCombo(int length) {
        List<String> combos = letterCombinations.get(length);
        int randomIndex = ThreadLocalRandom.current().nextInt(combos.size());
        return combos.get(randomIndex).toUpperCase();
    }

    public String getRandomCombo() {
        return getRandomCombo(2);
    }

    public boolean isWord(String word) {
        return words.contains(word);
    }

    /**
     * Generate all possible 2-letter, 3-letter, and 4-letter combinations of the provided words.
     * 
     * @param words the words to generate the combinations of.
     */
    private void addWordCombinations(Set<String> words) {
        for (int length = 2; length <= 4; length++) {
            addWordCombinations(words, length);
        }
    }

    private void addWordCombinations(Set<String> words, int length) {
        for (String word : words) {
            addWordCombinations(word, length);
        }
    }

    private void addWordCombinations(String word, int length) {
        List<String> combos = letterCombinations.get(length);

        if (combos == null) {
            combos = new ArrayList<>();
            letterCombinations.put(length, combos);
        }

        addWordCombinations(word, length, combos);
    }

    private void addWordCombinations(String word, int length, Collection<String> collection) {
        for (int start = 0; start < word.length()
            && start + length <= word.length(); start++) {

            String combo = word.substring(start, start + length);

            if (combo.contains(" ")) {
                continue;
            }

            collection.add(combo);
        }
    }
}
