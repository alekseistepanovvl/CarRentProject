package com.rent.carrent.service.user;

import com.rent.carrent.dto.user.UserDto;
import com.rent.carrent.mapper.UserMapper;
import com.rent.carrent.model.User;
import com.rent.carrent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public List<UserDto> getUserList() {
        List<User> userList = repository.findAll();
        return mapper.toDtoList(userList);
    }
}
