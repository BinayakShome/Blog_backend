package com.binayak.blog_backend.repo;

import com.binayak.blog_backend.entity.Category;
import com.binayak.blog_backend.entity.Post;
import com.binayak.blog_backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Integer> {
    Page<Post> findByUser(User user, Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);
    Page<Post> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}