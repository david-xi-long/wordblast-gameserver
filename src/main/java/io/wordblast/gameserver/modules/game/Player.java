package io.wordblast.gameserver.modules.game;

import java.net.Socket;
import java.util.UUID;

/**
 * Represents a player within a game.
 */
public class Player {
    private UUID uuid;
    private String username;
    private boolean state;
    private int lives;
    private int score;
    private boolean authenticated;
    // private Socket connection;

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
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

    // public Socket getConnection() {
    // return connection;
    // }

    // public void setConnection(Socket connection) {
    // this.connection = connection;
    // }
}
