package io.wordblast.gameserver.modules.game;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.messaging.rsocket.RSocketRequester;

/**
 * Represents a player within a game.
 */
public class Player {
    private final UUID uid;
    private final boolean authenticated;

    private int experience;

    private String username;
    private Map<String, Object> bigHeadOptions;
    private boolean ready;
    private PlayerState state;
    private int lives;

    private Set<Character> usedChars = new LinkedHashSet<>();
    private Set<Character> unusedChars = new LinkedHashSet<>();
    private Set<Character> newlyUsedChars = new LinkedHashSet<>();
    private Set<String> usedWords = new LinkedHashSet<>();
    private String email;
    private int timeElapsed;

    private RSocketRequester connection;

    /**
     * Creates a new Player object.
     * 
     * @param username the username of the player.
     */
    public Player(String username, UUID uid) {
        this.username = username;
        this.state = PlayerState.ACTIVE;

        if (uid != null) {
            this.uid = uid;
            this.authenticated = true;
        } else {
            this.uid = UUID.randomUUID();
            this.authenticated = false;
        }

        resetChars();
    }

    public UUID getUid() {
        return uid;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public Map<String, Object> getBigHeadOptions() {
        return bigHeadOptions;
    }

    public void setBigHeadOptions(Map<String, Object> bigHeadOptions) {
        this.bigHeadOptions = bigHeadOptions;
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

    /**
     * Resets the used characters of the player.
     */
    public void resetChars() {
        usedChars.clear();
        // newlyUsedChars.clear();
        for (char c = 'a'; c <= 'z'; c++) {
            unusedChars.add(c);
        }
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void incrementExperience(int experience) {
        this.experience += experience;
    }

    public int getExperience() {
        return experience;
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

    public void addTimeElapsed(int timeElapsed) {
        this.timeElapsed = timeElapsed;
    }
}
