package io.wordblast.gameserver.modules.game;

import java.util.Iterator;
import java.util.List;
import io.wordblast.gameserver.modules.word.WordInfo;
import io.wordblast.gameserver.modules.word.WordUtils;
import reactor.core.publisher.Mono;

/**
 * Various methods to help run game logic.
 */
public class GameService {
    private Game game;
    private WordUtils wordUtils;

    public GameService(Game game) {
        this.game = game;
    }

    public String generateLetterCombination() {
        return "";
    }

    /**
     * Check if the word has already been used in the game.
     * 
     * @param word the word to check.
     * @return {@code true} if the word has been used, otherwise {@code false}.
     */
    public boolean checkWord(String word) {
        Player currentPlayer = game.getCurrentPlayer();
        // Check list of used Words.
        if (game.getWords().contains(word)) {
            return false;
        }

        // Check if word contains current letter combination.
        // NULL CHECK because we have not implemented letter combo generating yet
        if (game.getCurrentLetterCombo() != null) { 
            if (!word.contains(game.getCurrentLetterCombo())) {
                return false;
            }
        }
        game.addWord(word);
        // TODO: Check other constraints.

        // Check player's used chars.
        List<Character> usedChars = currentPlayer.getUsedChars();
        List<Character> unusedChars = currentPlayer.getUnusedChars();
        List<Character> newlyUsedChars = currentPlayer.getNewlyUsedChars();
        newlyUsedChars.clear();

        Iterator<Character> iter = unusedChars.iterator();
        while (iter.hasNext()) {
            Character c = iter.next();
            if (word.indexOf(c) != -1) {
                iter.remove();
                usedChars.add(c);
                newlyUsedChars.add(c);
            }
        }

        if (unusedChars.size() == 0) {
            currentPlayer.resetChars();
            currentPlayer.setLives(currentPlayer.getLives() + 1);
        }
        game.setPreviousOutOfTime(false);
        // TODO: Calculate value of word.
        game.getController().nextTurn();
        return true;
    }

    /**
     * Starts the game.
     * 
     * @param game the game to start.
     */
    public void startGame(Game game) {
        game.setStatus(GameStatus.STARTED);
    }

    public Game gameplay(Game game) {
        return game;
    }

    public Game endGame(Game game) {
        return game;
    }
}
