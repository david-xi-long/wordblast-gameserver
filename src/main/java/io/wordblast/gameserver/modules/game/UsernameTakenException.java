package io.wordblast.gameserver.modules.game;

/**
 * An exception which is thrown when a user attempts to join a game, and that game already has a
 * player with the provided username.
 */
public class UsernameTakenException extends Exception {
    public UsernameTakenException() {
        super("Username is taken.");
    }
}
