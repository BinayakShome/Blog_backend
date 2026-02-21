package com.binayak.blog_backend.controllers;

import com.binayak.blog_backend.config.AppConstants;
import com.binayak.blog_backend.payloads.ApiResponse;
import com.binayak.blog_backend.payloads.PostDto;
import com.binayak.blog_backend.payloads.PostResponse;
import com.binayak.blog_backend.services.FileService;
import com.binayak.blog_backend.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody PostDto postDto,
            @PathVariable("userId") Integer userId,
            @PathVariable("categoryId") Integer categoryId) {

        PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getAllPostsByUser(
            @PathVariable("userId") Integer userId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {

        PostResponse getPosts = this.postService.getPostByUser(userId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getPosts, HttpStatus.FOUND);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getAllPostsByCategory(
            @PathVariable("categoryId") Integer categoryId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        PostResponse getPosts = this.postService.getPostByCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getPosts, HttpStatus.FOUND);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {

        PostResponse getPosts = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
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

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<PostResponse> searchPost(
            @PathVariable("keyword") String keyword,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        PostResponse getPost = this.postService.searchPost(keyword, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getPost, HttpStatus.FOUND);
    }

    @PostMapping("/post/image/upload/{post_id}")
    public ResponseEntity<PostDto> postImage(
            @PathVariable("post_id") Integer post_id,
            @RequestParam("image") MultipartFile image
            ) throws IOException {

        PostDto postDto = this.postService.getPostById(post_id);

        String filename = this.fileService.uploadImage(path, image);
        postDto.setImageName(filename);
        this.postService.updatePost(postDto, post_id);

        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping(value = "/post/image/{imageName}")
    public void downLoadImage(
        @PathVariable("imageName") String imageName,
        HttpServletResponse response
    ) throws IOException {
        InputStream res = this.fileService.getResources(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(res, response.getOutputStream());
    }
}