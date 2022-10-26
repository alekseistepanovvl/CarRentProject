package com.rent.carrent.dto.reservation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ReservationRequestDto {

    @Schema(description = "Start reservation date and time",
            pattern = "yyyy-MM-dd`T`HH:mm",
            example = "2022-10-26T19:40")
    @NotNull(message = "Reservation start date and time must be defined")
    @DateTimeFormat(pattern = "yyyy-MM-dd`T`HH:mm")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startReservation;

    @Schema(description = "End reservation date and time",
            pattern = "yyyy-MM-dd`T`HH:mm",
            example = "2022-10-26T21:40")
    @NotNull(message = "Reservation end date and time must be defined")
    @DateTimeFormat(pattern = "yyyy-MM-dd`T`HH:mm")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endReservation;
}
