package com.rent.carrent.dto.car;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class CarCreateRequestDto {

    @Schema(description = "Car model")
    @NotEmpty(message = "model name must be filled")
    private String model;

    @Schema(description = "Car make")
    @NotEmpty(message = "make name must me filled")
    private String make;

    @Schema(description = "Car's number")
    @NotEmpty(message = "Register number  must be filled")
    @Pattern(regexp = "C\\d{3}", message = "Car's register number have to math CXXX pattern")
    private String uniqueIdentifier;
}
