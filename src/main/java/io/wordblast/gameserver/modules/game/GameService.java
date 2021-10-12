package io.wordblast.gameserver.modules.game;

import org.springframework.stereotype.Service;

import java.util.UUID;

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

    public boolean checkWord(String word) {
        // Check list of used Words

        // Check if word contains current letter combination

        // Check other constraints

        // Calculate value of word
        return true;
    }

    public Game startGame(Game game) {
        game.setStatus(GameStatus.STARTED);

        return game;
    }

    public Game gameplay(Game game) {
        
        return game;
    }

    public Game endGame(Game game) {

        return game;
    }
    


}
