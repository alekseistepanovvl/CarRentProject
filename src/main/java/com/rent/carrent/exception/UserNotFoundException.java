package com.rent.carrent.exception;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String message, Object... params) {
        super(message, params);
    }
}
