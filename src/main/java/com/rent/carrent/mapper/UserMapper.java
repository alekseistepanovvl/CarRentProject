package com.rent.carrent.mapper;

import com.rent.carrent.dto.user.UserDto;
import com.rent.carrent.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    UserDto toDto(User model);

    List<UserDto> toDtoList(List<User> modelList);
}
