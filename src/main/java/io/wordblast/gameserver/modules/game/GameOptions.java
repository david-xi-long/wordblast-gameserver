package io.wordblast.gameserver.modules.game;

/**
 * The settings of the game lobby.
 */
public class GameOptions {
    private int maxPlayers;
    private int livesPerPlayer;
    private int timePerPlayer;
    private int decrementTimePerRound;
    private boolean earnExtraLives;
    private boolean increaseDifficulty;

    /**
     * Creates a new game options object with the default values.
     */
    public GameOptions() {
        this(new GameOptionsBuilder());
    }

    /**
     * Creates a new game options object with values provided from the builder.
     */
    public GameOptions(GameOptionsBuilder builder) {
        this.maxPlayers = builder.getMaxPlayers();
        this.livesPerPlayer = builder.getLivesPerPlayer();
        this.timePerPlayer = builder.getTimePerRound();
        this.decrementTimePerRound = builder.getDecrementTimePerRound();
        this.earnExtraLives = builder.isEarnExtraLives();
        this.increaseDifficulty = builder.isIncreaseDifficulty();
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setLivesPerPlayer(int lives) {
        this.livesPerPlayer = lives;
    }

    public int getLivesPerPlayer() {
        return livesPerPlayer;
    }

    public void setTimePerPlayer(int time) {
        this.timePerPlayer = time;
    }

    public int getTimePerPlayer() {
        return timePerPlayer;
    }

    public void setDecrementTimePerRound(int decrementTimePerRound) {
        this.decrementTimePerRound = decrementTimePerRound;
    }

    public int getDecrementTimePerRound() {
        return decrementTimePerRound;
    }

    public void setEarnExtraLives(boolean earnExtraLives) {
        this.earnExtraLives = earnExtraLives;
    }

    public boolean earnsExtraLives() {
        return earnExtraLives;
    }

    public void setIncreaseDifficulty(boolean increaseDifficulty) {
        this.increaseDifficulty = increaseDifficulty;
    }

    public boolean increasesDifficulty() {
        return increaseDifficulty;
    }
}
