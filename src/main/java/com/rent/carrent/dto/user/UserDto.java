package com.rent.carrent.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserDto {

    @Schema(description = "User identity")
    private String id;

    @Schema(description = "User full name")
    private String name;
}
