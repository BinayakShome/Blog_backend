package com.binayak.blog_backend.services;

import com.binayak.blog_backend.payloads.CommentDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);
    void deleteComment(Integer comment_id);
}