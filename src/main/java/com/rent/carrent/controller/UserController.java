package com.rent.carrent.controller;

import com.rent.carrent.dto.error.ErrorDto;
import com.rent.carrent.dto.user.UserDto;
import com.rent.carrent.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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
}
