package io.wordblast.gameserver.modules.game;

/**
 * A class representing a simplified view of the player object.
 */
public class PlayerInfo {
    private final String username;
    private final boolean ready;

    public PlayerInfo(String username, boolean ready) {
        this.username = username;
        this.ready = ready;
    }

    public String getUsername() {
        return username;
    }

    public boolean isReady() {
        return ready;
    }
}
