package com.rent.carrent.dto.car;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CarDto {

    @Schema(description = "Car identity")
    private String id;

    @Schema(description = "Car model")
    private String model;

    @Schema(description = "Car make")
    private String make;

    @Schema(description = "Car's number")
    private String uniqueIdentifier;
}
