package com.binayak.blog_backend.services;

import com.binayak.blog_backend.payloads.CategoryDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
    CategoryDto getCategoryByID(Integer categoryId);
    List<CategoryDto> getAllCategory();
    void deleteCategory(Integer categoryId);
}
