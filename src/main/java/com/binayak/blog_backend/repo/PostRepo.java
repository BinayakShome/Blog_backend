package com.binayak.blog_backend.repo;

import com.binayak.blog_backend.entity.Category;
import com.binayak.blog_backend.entity.Post;
import com.binayak.blog_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
}
