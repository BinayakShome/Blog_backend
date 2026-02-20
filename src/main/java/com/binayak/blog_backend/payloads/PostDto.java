package com.binayak.blog_backend.payloads;

import com.binayak.blog_backend.entity.Category;
import com.binayak.blog_backend.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private int post_id;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 4, message = "Title must be at least 4 characters")
    private String title;

    private String content;

    private Date uploadDate;

    private CategoryDto category;

    private UserDto user;

    private String imageName;
}
