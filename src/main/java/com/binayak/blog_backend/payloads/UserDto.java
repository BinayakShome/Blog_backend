package com.binayak.blog_backend.payloads;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, message = "Username must be of minimum 3 characters")
    private String name;

    @Email(message = "Email address is not valid!")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 4, max = 10, message = "Password must be minimum 4 chars and maximum 10 chars")
    private String password;

    private String about;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits and contain only numbers")
    private String number;
}