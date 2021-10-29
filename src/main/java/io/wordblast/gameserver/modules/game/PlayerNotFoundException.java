package io.wordblast.gameserver.modules.game;

/**
 * An exception which is thrown when a player that does not exist is attempted to be retrieved.
 */
public class PlayerNotFoundException extends Exception {
    public PlayerNotFoundException() {
        super("Player not found.");
    }
}
