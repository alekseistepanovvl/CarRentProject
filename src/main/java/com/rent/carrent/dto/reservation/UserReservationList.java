package com.rent.carrent.dto.reservation;

import com.rent.carrent.dto.user.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class UserReservationList {

    @Schema(description = "User information")
    private UserDto user;

    @Schema(description = "Reservation information")
    private List<ReservationDto> reservation;
}
