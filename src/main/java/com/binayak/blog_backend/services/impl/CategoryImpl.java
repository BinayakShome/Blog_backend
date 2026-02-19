package com.binayak.blog_backend.services.impl;

import com.binayak.blog_backend.entity.Category;
import com.binayak.blog_backend.exception.ResourceNotFoundException;
import com.binayak.blog_backend.payloads.CategoryDto;
import com.binayak.blog_backend.repo.CategoryRepo;
import com.binayak.blog_backend.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category addedcategory = this.categoryRepo.save(category);
        return this.modelMapper.map(addedcategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        // 1. Find the existing category or throw your custom exception
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        // 2. Update the fields (Checking for null prevents accidental blank updates!)
        if (categoryDto.getCategory_title() != null && !categoryDto.getCategory_title().trim().isEmpty()) {
            category.setCategory_title(categoryDto.getCategory_title());
        }

        if (categoryDto.getCategory_description() != null) {
            category.setCategory_description(categoryDto.getCategory_description());
        }

        // 3. Save the updated entity to the database
        Category updatedCategory = this.categoryRepo.save(category);

        // 4. Use ModelMapper to convert the saved Entity back to a DTO!
        return this.modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryByID(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos = categories
                .stream()
                .map(cat -> this.modelMapper.map(cat, CategoryDto.class))
                .collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));
        categoryRepo.delete(category);
    }
}