package io.wordblast.gameserver.modules.authentication;

/**
 * An exception which is thrown when a user attempts to log in to a user account, and an account
 * with the user's provided information is not found.
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User was not found.");
    }
}
