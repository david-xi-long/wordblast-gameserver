package io.wordblast.gameserver.modules.word;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * A utility class which parses words and letter combinations.
 */
@Component
public final class WordManager {
    private static Map<String, Integer> parsedWords;
    private static Map<Integer, Map<String, Integer>> parsedCombinations;

    /**
     * Class cannot be instantiated.
     */
    private WordManager() {}

    /**
     * Parses the input word combinations.
     * 
     * @throws IOException thrown if the words file could not be read.
     */
    @EventListener(ApplicationReadyEvent.class)
    public static void parseWords() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        parsedWords = mapper.readValue(
            new ClassPathResource("words.json").getInputStream(),
            new TypeReference<Map<String, Integer>>() {});

        parsedCombinations = mapper.readValue(
            new ClassPathResource("combos.json").getInputStream(),
            new TypeReference<Map<Integer, Map<String, Integer>>>() {});
    }

    public static Map<String, Integer> getParsedWords() {
        return parsedWords;
    }

    public static Map<Integer, Map<String, Integer>> getParsedCombinations() {
        return parsedCombinations;
    }
}
