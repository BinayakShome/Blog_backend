package com.binayak.blog_backend.services.impl;

import com.binayak.blog_backend.entity.Category;
import com.binayak.blog_backend.entity.Post;
import com.binayak.blog_backend.entity.User;
import com.binayak.blog_backend.exception.ResourceNotFoundException;
import com.binayak.blog_backend.payloads.PostDto;
import com.binayak.blog_backend.payloads.PostResponse;
import com.binayak.blog_backend.repo.CategoryRepo;
import com.binayak.blog_backend.repo.PostRepo;
import com.binayak.blog_backend.repo.UserRepo;
import com.binayak.blog_backend.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Post post = this.postRepo.findById(post_id).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", post_id));

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
        Post post = this.postRepo.findById(post_id).orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", post_id));
        postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable page = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = this.postRepo.findAll(page);
        List<Post> allPosts = pagePost.getContent();

        List<PostDto> posts = allPosts.stream().map((post -> this.modelMapper.map(post, PostDto.class))).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(posts);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setLastPage(pagePost.isLast());
        postResponse.setTotalElements(pagePost.getNumberOfElements());
        postResponse.setTotalPages(pagePost.getTotalPages());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer post_id) {
        Post post = this.postRepo.findById(post_id).orElseThrow(() -> new ResourceNotFoundException("post", "id", post_id));

        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostResponse getPostByUser(Integer user_id, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        User user = this.userRepo.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
        Page<Post> pagePost = this.postRepo.findByUser(user, pageable);

        List<Post> allPosts = pagePost.getContent();
        List<PostDto> postDtos = allPosts.stream().map(posts -> this.modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);

        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostResponse getPostByCategory(Integer category_id, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Category category = this.categoryRepo.findById(category_id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", category_id));

        Page<Post> allPosts = this.postRepo.findByCategory(category, pageable);
        List<PostDto> postsByCategory = allPosts.stream().map((posts -> this.modelMapper.map(posts, PostDto.class))).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postsByCategory);

        postResponse.setPageNumber(allPosts.getNumber());
        postResponse.setPageSize(allPosts.getSize());
        postResponse.setTotalElements(allPosts.getTotalElements());
        postResponse.setTotalPages(allPosts.getTotalPages());
        postResponse.setLastPage(allPosts.isLast());

        return postResponse;
    }

    @Override
    public PostResponse searchPost(String keyword, Integer pageNumber, Integer pageSize) {
        return null;
    }

}
