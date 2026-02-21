package com.binayak.blog_backend.repo;

import com.binayak.blog_backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
