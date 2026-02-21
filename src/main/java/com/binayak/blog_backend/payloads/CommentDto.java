package com.binayak.blog_backend.payloads;

import com.binayak.blog_backend.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private int comment_id;
    @NotBlank(message = "Cannot accept null character")
    @Size(max = 1000, message = "Maximum character: 1000")
    private String content;
    private Date commentDateAndTime;
    private UserDto user;
}
