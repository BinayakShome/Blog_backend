package com.binayak.blog_backend.services.impl;

import com.binayak.blog_backend.entity.Category;
import com.binayak.blog_backend.entity.Post;
import com.binayak.blog_backend.entity.User;
import com.binayak.blog_backend.exception.ResourceNotFoundException;
import com.binayak.blog_backend.payloads.PostDto;
import com.binayak.blog_backend.repo.CategoryRepo;
import com.binayak.blog_backend.repo.PostRepo;
import com.binayak.blog_backend.repo.UserRepo;
import com.binayak.blog_backend.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer user_id, Integer category_id) {

        //fetch user with user_id
        User user = this.userRepo.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("user", "id", user_id));
        //fetch category with category_id
        Category category = this.categoryRepo.findById(category_id).orElseThrow(() -> new ResourceNotFoundException("category", "id", category_id));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setUploadDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        this.postRepo.save(post);
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer post_id) {
        // 1. Find the existing post
        Post post = this.postRepo.findById(post_id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", post_id));

        // 2. Update title ONLY if Postman actually sent a new one
        if (postDto.getTitle() != null && !postDto.getTitle().trim().isEmpty()) {
            post.setTitle(postDto.getTitle());
        }

        // 3. Update content ONLY if Postman actually sent new content
        if (postDto.getContent() != null && !postDto.getContent().trim().isEmpty()) {
            post.setContent(postDto.getContent());
        }

        // 4. Update image ONLY if Postman actually sent a new image name
        if (postDto.getImageName() != null && !postDto.getImageName().trim().isEmpty()) {
            post.setImageName(postDto.getImageName());
        }

        // 5. Save the updated post back to the database
        Post updatedPost = this.postRepo.save(post);

        // 6. Convert and return
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer post_id) {
        Post post = this.postRepo.findById(post_id).orElseThrow(()-> new ResourceNotFoundException("Post", "post_id", post_id));
        postRepo.delete(post);
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> allPosts = this.postRepo.findAll();
        List<PostDto> posts = allPosts.stream().map((post -> this.modelMapper.map(post, PostDto.class))).collect(Collectors.toList());

        return posts;
    }

    @Override
    public PostDto getPostById(Integer post_id) {
        Post post = this.postRepo.findById(post_id).orElseThrow(() -> new ResourceNotFoundException("post", "id", post_id));

        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostByUser(Integer user_id) {
        User user = this.userRepo.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
        List<Post> allPosts = this.postRepo.findByUser(user);

        return allPosts.stream().map((posts -> this.modelMapper.map(posts, PostDto.class))).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostByCategory(Integer category_id) {
        Category category = this.categoryRepo.findById(category_id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", category_id));

        List<Post> allPosts = this.postRepo.findByCategory(category);
        List<PostDto> postsByCategory = allPosts.stream().map((posts -> this.modelMapper.map(posts, PostDto.class))).collect(Collectors.toList());

        return postsByCategory;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        return List.of();
    }
}
