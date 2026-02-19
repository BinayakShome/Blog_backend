package com.binayak.blog_backend.services.impl;

import com.binayak.blog_backend.entity.User;
import com.binayak.blog_backend.exception.ResourceNotFoundException;
import com.binayak.blog_backend.payloads.UserDto;
import com.binayak.blog_backend.repo.UserRepo;
import com.binayak.blog_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        // Only update if the incoming name is NOT null
        if(userDto.getName() != null && !userDto.getName().isEmpty()) {
            user.setName(userDto.getName());
        }

        // Only update if the incoming email is NOT null
        if(userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
            user.setEmail(userDto.getEmail());
        }

        // Only update password if provided
        if(userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(userDto.getPassword());
        }

        // About can be null, so simple update is fine, or check if you prefer
        if(userDto.getAbout() != null) {
            user.setAbout(userDto.getAbout());
        }

        if (userDto.getNumber() != null && !userDto.getNumber().isEmpty()) {
            user.setNumber(userDto.getNumber());
        }

        User updatedUser = this.userRepo.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepo.delete(user);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
//        List<User> users = new ArrayList<>();
//        return users.stream()
//                .map(this::userToDto)
//                .collect(Collectors.toList());

        List<User> users = this.userRepo.findAll();
        List<UserDto> userDto = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDto;
    }

    //Later will use mapper class for this
    private User dtoToUser(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setNumber(dto.getNumber());
        user.setPassword(dto.getPassword());
        user.setAbout(dto.getAbout());
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setNumber(user.getNumber());
        dto.setPassword(user.getPassword());
        dto.setAbout(user.getAbout());
        return dto;
    }
}