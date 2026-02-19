package com.binayak.blog_backend.services;

import com.binayak.blog_backend.payloads.UserDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer userId);

    void deleteUser(Integer userId);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUser();
}