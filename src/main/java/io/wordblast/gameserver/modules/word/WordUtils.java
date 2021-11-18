package io.wordblast.gameserver.modules.word;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

/**
 * A class which provides helper methods to provide letter combinations and word information.
 */
public final class WordUtils {
    private static final WebClient CLIENT =
        WebClient.create("https://api.dictionaryapi.dev/api/v2/entries");

    /**
     * Class cannot be instantiated.
     */
    private WordUtils() {
        throw new IllegalStateException("Class cannot be instantiated.");
    }

    /**
     * Retrieves the information related to a word.
     * 
     * @param word the word to retrieve the information of.
     * @return the information related to the word, if found. Otherwise {@code null}.
     */
    public static Mono<Optional<WordInfo>> getWordInfo(String word) {
        String lowerCaseWord = word.toLowerCase();

        if (!WordManager.getParsedWords().containsKey(lowerCaseWord)) {
            return Mono.just(Optional.empty());
        }

        return CLIENT.get()
            .uri("/en/" + lowerCaseWord)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<WordResponse[]>() {})
            .onErrorResume(WebClientException.class, (ex) -> Mono.empty())
            .map((responses) -> {
                WordResponse response = responses[0];
                WordMeaning meaning = response.getMeanings()[0];
                WordDefinition definition = meaning.getDefinitions()[0];

                return new WordInfo(
                    response.getWord(),
                    meaning.getPartOfSpeech(),
                    definition.getDefinition());
            })
            .map((info) -> Optional.of(info));
    }

    /**
     * Return a random 2-letter combination.
     * 
     * @return the 2-letter combination.
     */
    public static String getCombination() {
        // For now, only 2-letter combinations with a frequency > 2450 are returned.
        return getCombination(2);
    }

    private static String getCombination(int length) {
        return randomCombination(length);
    }

    private static int randomLength() {
        Collection<Integer> lengths = WordManager.getParsedCombinations().keySet();
        List<Integer> lengthsCopy = new ArrayList<>(lengths);
        int randomIndex = ThreadLocalRandom.current().nextInt(lengthsCopy.size());
        return lengthsCopy.get(randomIndex);
    }

    private static String randomCombination(int length) {
        Collection<String> combos = WordManager.getParsedCombinations().get(length).keySet();
        List<String> combosCopy = new ArrayList<>(combos);
        int randomIndex = ThreadLocalRandom.current().nextInt(combosCopy.size());
        return combosCopy.get(randomIndex).toUpperCase();
    }
}