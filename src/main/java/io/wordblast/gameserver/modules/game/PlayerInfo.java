package io.wordblast.gameserver.modules.game;

import java.util.Map;

/**
 * A class representing a simplified view of the player object.
 */
public class PlayerInfo {
    private final String username;
    private final Map<String, Object> bigHeadOptions;
    private final boolean ready;
    private final int lives;

    /**
     * Creates a new PlayerInfo instance.
     * 
     * @param username the username of the player.
     * @param bigHeadOptions the big head options of the player.
     * @param ready the ready state of the player.
     * @param lives the remaining lives of the player.
     */
    public PlayerInfo(String username, Map<String, Object> bigHeadOptions, boolean ready,
        int lives) {
        this.username = username;
        this.bigHeadOptions = bigHeadOptions;
        this.ready = ready;
        this.lives = lives;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Object> getBigHeadOptions() {
        return this.bigHeadOptions;
    }

    public boolean isReady() {
        return ready;
    }

    public int getLives() {
        return lives;
    }

    public static PlayerInfo of(Player player) {
        return new PlayerInfo(player.getUsername(), player.getBigHeadOptions(), player.isReady(),
            player.getLives());
    }
}
