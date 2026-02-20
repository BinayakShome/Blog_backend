package com.binayak.blog_backend.services;

import com.binayak.blog_backend.payloads.PostDto;
import com.binayak.blog_backend.payloads.PostResponse;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    PostDto createPost(PostDto postDto, Integer user_id, Integer category_id);

    PostDto updatePost(PostDto postDto, Integer post_id);

    void deletePost(Integer post_id);

    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PostDto getPostById(Integer post_id);

    PostResponse getPostByUser(Integer user_id, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PostResponse getPostByCategory(Integer category_id, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PostResponse searchPost(String keyword, Integer pageNumber, Integer pageSize);

}
