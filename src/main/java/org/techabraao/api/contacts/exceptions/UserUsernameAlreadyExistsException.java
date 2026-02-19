package org.techabraao.api.contacts.exceptions;

public class UserUsernameAlreadyExistsException extends RuntimeException {
    public UserUsernameAlreadyExistsException(String message) {
        super(message);
    }
}
