package io.wordblast.gameserver.modules.authentication;

/**
 * An exception which is thrown when a user attempts to create a user account,
 * and an account already exists with the user's provided information.
 */
public class UserDoesNotExistException extends Exception {
    public UserDoesNotExistException() {
        super("User does not exist.");
    }
}
