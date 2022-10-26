package com.rent.carrent.controller;

import com.rent.carrent.dto.error.ErrorDto;
import com.rent.carrent.dto.reservation.ReservationRequestDto;
import com.rent.carrent.dto.reservation.UserReservationList;
import com.rent.carrent.dto.user.UserDto;
import com.rent.carrent.service.reservation.ReservationService;
import com.rent.carrent.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final ReservationService reservationService;

    @GetMapping
    @Operation(summary = "Get user list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User list",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))
                    }),
            @ApiResponse(responseCode = "500", description = "Unexpected exception",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDto.class))})
    })
    public List<UserDto> getUserList() {
        return userService.getUserList();
    }


    @PostMapping("/{userId}/reservation")
    @Operation(summary = "Reserve available car for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation result",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserReservationList.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validation exception",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found exception",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDto.class))}),
            @ApiResponse(responseCode = "500", description = "Unexpected exception",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDto.class))})
    })
    public UserReservationList reserveCar(@PathVariable String userId,
                                          @RequestBody @Valid ReservationRequestDto requestDto) {
        return reservationService.reserveCar(userId, requestDto);
    }

    @GetMapping("/{userId}/reservation")
    @Operation(summary = "Get upcoming reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upcoming reservations",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserReservationList.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Not found exception",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDto.class))}),
            @ApiResponse(responseCode = "500", description = "Unexpected exception",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDto.class))})
    })
    public UserReservationList getUpcomingReservationList(@PathVariable String userId) {
        return reservationService.getUpcomingReservationList(userId);
    }
}
