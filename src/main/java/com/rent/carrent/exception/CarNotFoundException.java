package com.rent.carrent.exception;

public class CarNotFoundException extends NotFoundException {

    public CarNotFoundException(String message, Object... params) {
        super(message, params);
    }
}
