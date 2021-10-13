package io.wordblast.gameserver.modules.game;

/**
 * Various methods to help run game logic.
 */
public class GameService {
    private Game game;

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
        // TODO: Check list of used Words.
        // TODO: Check if word contains current letter combination.
        // TODO: Check other constraints.
        // TODO: Calculate value of word.

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
