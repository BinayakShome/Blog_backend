package com.binayak.blog_backend.controllers;

import com.binayak.blog_backend.payloads.ApiResponse;
import com.binayak.blog_backend.payloads.UserDto;
import com.binayak.blog_backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    //POST - CREATE USERS
    //PUT - UPDATE USER
    //DELETE - DELETE USER
    //GET - GET USER / USERS

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) {
        UserDto newUser = this.userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> allUsers = this.userService.getAllUser();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer userId) {
        UserDto user = this.userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable("userId") Integer userId) {
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User deleted", true), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("userId") Integer userId, @RequestBody UserDto userDto) {
        UserDto updatedUser = this.userService.updateUser(userDto, userId);

        return ResponseEntity.ok(updatedUser);
    }
}