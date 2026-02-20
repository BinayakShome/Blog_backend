package com.binayak.blog_backend.controllers;

import com.binayak.blog_backend.payloads.ApiResponse;
import com.binayak.blog_backend.payloads.PostDto;
import com.binayak.blog_backend.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody PostDto postDto,
            @PathVariable("userId") Integer userId,
            @PathVariable("categoryId") Integer categoryId) {

        PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostsByUser(@PathVariable("userId") Integer userId) {

        List<PostDto> getPosts = this.postService.getPostByUser(userId);
        return new ResponseEntity<>(getPosts, HttpStatus.FOUND);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostsByCategory(@PathVariable("categoryId") Integer categoryId) {

        List<PostDto> getPosts = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<>(getPosts, HttpStatus.FOUND);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts() {

        List<PostDto> getPosts = this.postService.getAllPost();
        return new ResponseEntity<>(getPosts, HttpStatus.FOUND);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId) {
        PostDto post = this.postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.FOUND);
    }

    @DeleteMapping("/posts/{post_id}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("post_id") Integer post_id) {
        this.postService.deletePost(post_id);
        return new ResponseEntity<>(new ApiResponse("Post Deleted", true), HttpStatus.OK);
    }

    @PutMapping("/posts/{post_id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("post_id") Integer post_id,
                                              @RequestBody PostDto postDto) {
        PostDto updatedPost = this.postService.updatePost(postDto, post_id);
        return ResponseEntity.ok(updatedPost);
    }
}
