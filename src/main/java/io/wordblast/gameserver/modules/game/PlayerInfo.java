package io.wordblast.gameserver.modules.game;

/**
 * A class representing a simplified view of the player object.
 */
public class PlayerInfo {
    private final String username;
    private final boolean ready;
    private final int lives;

    /**
     * Creates a new PlayerInfo instance.
     * 
     * @param username the username of the player.
     * @param ready the ready state of the player.
     * @param lives the remaining lives of the player.
     */
    public PlayerInfo(String username, boolean ready, int lives) {
        this.username = username;
        this.ready = ready;
        this.lives = lives;
    }

    public String getUsername() {
        return username;
    }

    public boolean isReady() {
        return ready;
    }

    public int getLives() {
        return lives;
    }

    public static PlayerInfo of(Player player) {
        return new PlayerInfo(player.getUsername(), player.isReady(), player.getLives());
    }
}
