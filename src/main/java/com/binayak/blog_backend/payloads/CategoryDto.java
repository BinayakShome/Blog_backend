package com.binayak.blog_backend.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private int category_id;

    @NotBlank(message = "Category title cannot be empty")
    @Size(min = 3, message = "Category title must be of minimum 3 characters")
    private String category_title;

    @Size(max = 50, message = "Description cannot exceed 50 characters")
    private String category_description;
}
