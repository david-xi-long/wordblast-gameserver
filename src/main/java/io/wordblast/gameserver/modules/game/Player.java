package io.wordblast.gameserver.modules.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.messaging.rsocket.RSocketRequester;

/**
 * Represents a player within a game.
 */
public class Player {
    private final UUID uid = UUID.randomUUID();

    private String username;
    private boolean ready;
    private boolean state;
    private int lives;
    private int score;
    private boolean authenticated;
    private RSocketRequester connection;
    private List<Character> usedChars = new ArrayList<>();
    private List<Character> unusedChars = new ArrayList<>(); 

    public Player(String username) {
        this.username = username;
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

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
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

    public List<Character> getUnusedChars() {
        return unusedChars;
    }

    public List<Character> getUsedChars() {
        return usedChars;
    }

    public void resetChars() {
        usedChars.clear();
        for (char c = 'A'; c <= 'Z'; c++) {
            unusedChars.add(c);
        }
    }
}
