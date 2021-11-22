package io.wordblast.gameserver.modules.game;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import org.springframework.messaging.rsocket.RSocketRequester;

/**
 * Represents a player within a game.
 */
public class Player {
    private final UUID uid = UUID.randomUUID();

    private String username;
    private boolean ready;
    private PlayerState state;
    private int lives;
    private int score;
    private int xp;
    private boolean authenticated;
    private RSocketRequester connection;
    private Set<Character> usedChars = new LinkedHashSet<>();
    private Set<Character> unusedChars = new LinkedHashSet<>();
    private Set<Character> newlyUsedChars = new LinkedHashSet<>();
    private Set<String> usedWords = new LinkedHashSet<>();
    private String email;
    private int timeElapsed;

    /**
     * Creates a new Player object.
     * 
     * @param username the username of the player.
     */
    public Player(String username) {
        this.username = username;
        this.state = PlayerState.ACTIVE;
        resetChars();
    }

    public UUID getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public RSocketRequester getConnection() {
        return connection;
    }

    public void setConnection(RSocketRequester connection) {
        this.connection = connection;
    }

    public Set<Character> getUnusedChars() {
        return unusedChars;
    }

    public Set<Character> getUsedChars() {
        return usedChars;
    }

    public void resetChars() {
        usedChars.clear();
        // newlyUsedChars.clear();
        for (char c = 'A'; c <= 'Z'; c++) {
            unusedChars.add(c);
        }
    }

    public void incrementXP(int xp) {
        this.xp += xp;
    }

    public int getXP() {
        return xp;
    }

    public Set<Character> getNewlyUsedChars() {
        return newlyUsedChars;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getUsedWords() {
        return usedWords;
    }

    public void addWord(String word) {
        this.usedWords.add(word);
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(int timeElapsed) {
        this.timeElapsed = timeElapsed;
    }
}
