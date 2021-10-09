package io.wordblast.gameserver.modules.game;

/**
 * An exception which is thrown when a user attempts to retrieve a game, and the game by the user's
 * provided information is not found.
 */
public class GameNotFoundException extends Exception {
    public GameNotFoundException() {
        super("Game was not found.");
    }
}
