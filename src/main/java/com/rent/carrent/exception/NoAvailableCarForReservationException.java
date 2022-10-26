package com.rent.carrent.exception;

public class NoAvailableCarForReservationException extends NotFoundException {

    public NoAvailableCarForReservationException(String message, Object... params) {
        super(message, params);
    }
}
