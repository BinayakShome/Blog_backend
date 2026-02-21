package com.binayak.blog_backend.services.impl;

import com.binayak.blog_backend.entity.Comment;
import com.binayak.blog_backend.entity.Post;
import com.binayak.blog_backend.entity.User;
import com.binayak.blog_backend.exception.ResourceNotFoundException;
import com.binayak.blog_backend.payloads.CommentDto;
import com.binayak.blog_backend.repo.CommentRepo;
import com.binayak.blog_backend.repo.PostRepo;
import com.binayak.blog_backend.repo.UserRepo;
import com.binayak.blog_backend.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setCommentDateAndTime(new Date());
        comment.setUser(user);
        comment.setPost(post);
        Comment savedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer comment_id) {
        Comment comment = this.commentRepo.findById(comment_id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", comment_id));
        this.commentRepo.delete(comment);
    }
}
