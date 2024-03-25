package com.marcuslull.mbyapisec.exception;

public class UsernameAlreadyExistsException extends UserRegistrationException {

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
