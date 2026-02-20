package com.binayak.blog_backend.services;

import com.binayak.blog_backend.payloads.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    PostDto createPost(PostDto postDto, Integer user_id, Integer category_id);

    PostDto updatePost(PostDto postDto, Integer post_id);

    void deletePost(Integer post_id);

    List<PostDto> getAllPost();

    PostDto getPostById(Integer post_id);

    List<PostDto> getPostByUser(Integer user_id);

    List<PostDto> getPostByCategory(Integer category_id);

    List<PostDto> searchPost(String keyword);
}
