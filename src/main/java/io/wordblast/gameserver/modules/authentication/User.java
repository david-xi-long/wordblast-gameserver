package io.wordblast.gameserver.modules.authentication;

import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The object representing a user account.
 */
@Document(collection = "users")
public class User {
    @Id
    private UUID uid;
    private String idAsString;
    private String email;
    private String hashedPassword;
    private int experience;
    private int gamesPlayed;
    private int totalWords;
    private int totalTimeElapsed;
    private double WPM;
    private int level;

    /**
     * Creates a new user object with the default values.
     */
    public User() {
        this(UUID.randomUUID(), null, null);
    }

    /**
     * Creates a new user object.
     * 
     * @param uid the unique identifier of the user.
     * @param email the email address of the user account.
     * @param hashedPassword the hashed password of the user account.
     */
    public User(UUID uid, String email, String hashedPassword) {
        this.uid = uid;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.experience = 0;
        this.gamesPlayed = 0;
        this.totalWords = 0;
        this.totalTimeElapsed = 0;
        this.WPM = 0;
        this.idAsString = uid.toString();
        this.level = 0;
    }

    public void setUid(UUID uuid) {
        this.uid = uuid;
    }

    public UUID getUid() {
        return uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public int getExperience() {
        return experience;
    }

    public void addExperience(int xp) {
        this.experience += xp;
    }

    public int getGamesPlayed() {
        return this.gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getTotalWords() {
        return this.totalWords;
    }

    public void setTotalWords(int totalWords) {
        this.totalWords = totalWords;
    }

    public int getTotalTimeElapsed() {
        return this.totalTimeElapsed;
    }

    public void setTotalTimeElapsed(int totalTimeElapsed) {
        this.totalTimeElapsed = totalTimeElapsed;
    }

    public double getWPM() {
        return this.WPM;
    }

    public void setWPM(int WPM) {
        this.WPM = WPM;
    }

    public String getIdAsString() {
        return this.idAsString;
    }

    public void setIdAsString(String idAsString) {
        this.idAsString = idAsString;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
