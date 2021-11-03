package io.wordblast.gameserver.modules.game;

/**
 * A builder class for {@link io.wordblast.gameserver.modules.game.GameOptions GameOptions}.
 */
// CHECKSTYLE.OFF: MissingJavadocMethodCheck
public final class GameOptionsBuilder {
    private GameVisibility visibility = GameVisibility.PUBLIC;
    private int maxPlayers = 8;
    private int livesPerPlayer = 3;
    private int timePerRound = 20;
    private int decrementTimePerRound = 1;
    private boolean earnExtraLives = true;
    private boolean increaseDifficulty = true;

    public GameOptionsBuilder maxPlayers(final int maxPlayers) {
        if (maxPlayers < 0) {
            return this;
        }
        this.maxPlayers = maxPlayers;
        return this;
    }

    public GameOptionsBuilder visibility(final GameVisibility visibility) {
        if (visibility == null) {
            return this;
        }
        this.visibility = visibility;
        return this;
    }

    public GameVisibility getVisibility() {
        return visibility;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public GameOptionsBuilder livesPerPlayer(final int livesPerPlayer) {
        if (livesPerPlayer < 0) {
            return this;
        }
        this.livesPerPlayer = livesPerPlayer;
        return this;
    }

    public int getLivesPerPlayer() {
        return livesPerPlayer;
    }

    public GameOptionsBuilder timePerRound(final int timePerRound) {
        if (timePerRound < 0) {
            return this;
        }
        this.timePerRound = timePerRound;
        return this;
    }

    public int getTimePerRound() {
        return timePerRound;
    }

    public GameOptionsBuilder decrementTimePerRound(final int decrementTimePerRound) {
        if (decrementTimePerRound < 0) {
            return this;
        }
        this.decrementTimePerRound = decrementTimePerRound;
        return this;
    }

    public int getDecrementTimePerRound() {
        return decrementTimePerRound;
    }

    public GameOptionsBuilder earnExtraLives(final boolean earnExtraLives) {
        this.earnExtraLives = earnExtraLives;
        return this;
    }

    public boolean isEarnExtraLives() {
        return earnExtraLives;
    }

    public GameOptionsBuilder increaseDifficulty(final boolean increaseDifficulty) {
        this.increaseDifficulty = increaseDifficulty;
        return this;
    }

    public boolean isIncreaseDifficulty() {
        return increaseDifficulty;
    }
}
// CHECKSTYLE.ON: MissingJavadocMethodCheck
