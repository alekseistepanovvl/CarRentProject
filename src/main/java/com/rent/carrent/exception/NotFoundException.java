package com.rent.carrent.exception;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException {
    private final Object[] params;

    public NotFoundException(String message, Object... params) {
        super(message);
        this.params = params;
    }
}
