package io.wordblast.gameserver.modules.game;

/**
 * An exception which is thrown when a user attempts to retrieve a game, and the game by the user's
 * provided information is not found.
 */
public class GameInProgressException extends Exception {
    public GameInProgressException() {
        super("Game is already in progress.");
    }
}
