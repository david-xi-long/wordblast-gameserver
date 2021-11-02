package io.wordblast.gameserver.modules.authentication;

/**
 * An exception which is sent when an unauthorized user attempts to make a request to an authorized
 * endpoint.
 */
public class NotAuthorizedException extends Exception {
    public NotAuthorizedException() {
        super("Request user is not authorized.");
    }
}
