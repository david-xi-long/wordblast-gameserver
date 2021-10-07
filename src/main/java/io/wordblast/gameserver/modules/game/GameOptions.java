package io.wordblast.gameserver.modules.game;

/**
 * The settings of the game lobby.
 */
public class GameOptions {
    private int maxPlayers;
    private int livesPerPlayer;
    private int timePerPlayer;

    /**
     * Creates a new game options object with the default values.
     */
    public GameOptions() {
        maxPlayers = 8;
        livesPerPlayer = 3;
        timePerPlayer = 20;
    }

    /**
     * Creates a new game options object.
     * 
     * @param players the maximum player count of the game.
     * @param lives the maximum lives of a player.
     * @param time the maximum time of a round.
     */
    public GameOptions(int players, int lives, int time) {
        maxPlayers = players;
        livesPerPlayer = lives;
        timePerPlayer = time;
    }

    public void setMaxPlayers(int count) {
        maxPlayers = count;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setLivesPerPlayer(int lives) {
        livesPerPlayer = lives;
    }

    public int getLivesPerPlayer() {
        return livesPerPlayer;
    }

    public void setTimePerPlayer(int time) {
        timePerPlayer = time;
    }

    public int getTimePerPlayer() {
        return timePerPlayer;
    }

}
