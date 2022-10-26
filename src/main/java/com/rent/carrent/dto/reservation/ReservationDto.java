package com.rent.carrent.dto.reservation;

import com.rent.carrent.dto.car.CarDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDto {

    @Schema(description = "Reservation identity")
    private String id;

    @Schema(description = "Start reservation date and time")
    private LocalDateTime reservationStart;

    @Schema(description = "End reservation date and time")
    private LocalDateTime reservationEnd;

    @Schema(description = "Car information")
    private CarDto car;
}
