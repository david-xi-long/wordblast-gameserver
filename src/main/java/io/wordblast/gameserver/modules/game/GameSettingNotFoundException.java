package io.wordblast.gameserver.modules.game;

/**
 * An exception thrown when a game setting that does not exist was attempted to be retrieved or
 * modified.
 */
public class GameSettingNotFoundException extends Exception {
    public GameSettingNotFoundException() {
        super("Game setting was not found.");
    }
}
