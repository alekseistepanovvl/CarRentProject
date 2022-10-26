package com.rent.carrent.controller;

import com.rent.carrent.dto.car.CarCreateRequestDto;
import com.rent.carrent.dto.car.CarDto;
import com.rent.carrent.dto.error.ErrorDto;
import com.rent.carrent.dto.user.UserDto;
import com.rent.carrent.service.car.CarService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/car")
@RequiredArgsConstructor
@Validated
public class CarController {
    private final CarService carService;

    @GetMapping
    @Operation(summary = "Get car list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car list",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CarDto.class)))
                    }),
            @ApiResponse(responseCode = "500", description = "Unexpected exception",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDto.class))})
    })
    public List<CarDto> getCarList() {
        return carService.getCarList();
    }

    @PostMapping
    @Operation(summary = "Create new car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New car added",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CarDto.class)))
                    }),
            @ApiResponse(responseCode = "400", description = "Validation exception",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDto.class))}),
            @ApiResponse(responseCode = "500", description = "Unexpected exception",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDto.class))})
    })
    public CarDto addCar(@RequestBody @Valid CarCreateRequestDto car) {
        return carService.addCar(car);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update car",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CarDto.class)))
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
    public CarDto updateCar(@PathVariable String id,
                            @RequestBody @Valid CarCreateRequestDto car) {
        return carService.updateCar(id, car);
    }
}
