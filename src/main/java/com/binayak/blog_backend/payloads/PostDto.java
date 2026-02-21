package com.binayak.blog_backend.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private int post_id;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 4, message = "Title must be at least 4 characters")
    private String title;

    @Size(max = 1000, message = "Maxmimum size: 1000 characters")
    private String content;

    private Date uploadDate;

    private CategoryDto category;

    private UserDto user;

    private String imageName;

    private List<CommentDto> post_comments = new ArrayList<>();
}
