package com.binayak.blog_backend.controllers;

import com.binayak.blog_backend.payloads.ApiResponse;
import com.binayak.blog_backend.payloads.CategoryDto;
import com.binayak.blog_backend.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto category) {
        CategoryDto newCategory = this.categoryService.createCategory(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @PathVariable("categoryId") Integer categoryId, @RequestBody CategoryDto categoryDto) {
        CategoryDto category = this.categoryService.updateCategory(categoryDto, categoryId);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        List<CategoryDto> categories = this.categoryService.getAllCategory();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") Integer categoryId) {
        CategoryDto category = this.categoryService.getCategoryByID(categoryId);
        return new ResponseEntity<>(category, HttpStatus.FOUND);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category removed", true), HttpStatus.OK);
    }
}
