package com.rent.carrent.exception;

import lombok.Data;

@Data
public class CarNotFoundException extends RuntimeException {
    private final Object[] params;

    public CarNotFoundException(String message, Object... params) {
        super(message);
        this.params = params;
    }

}
