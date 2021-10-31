package io.wordblast.gameserver.modules.authentication;

import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The object representing a user account.
 */
@Document(collection = "users")
public class User {
    private UUID uid;
    @Id
    private String email;
    private String hashedPassword;
    private int experience;

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
}
