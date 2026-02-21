package com.binayak.blog_backend.controllers;

import com.binayak.blog_backend.entity.Comment;
import com.binayak.blog_backend.payloads.ApiResponse;
import com.binayak.blog_backend.payloads.CommentDto;
import com.binayak.blog_backend.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{post_id}/user/{userId}/comment")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable("post_id") Integer post_id,
            @PathVariable("userId") Integer userId,
            @Valid @RequestBody CommentDto commentDto
    ) {
        CommentDto comment = this.commentService.createComment(commentDto, post_id, userId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{comment_id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("comment_id") Integer comment_id) {
        this.commentService.deleteComment(comment_id);

        return new ResponseEntity<>(new ApiResponse("Comment deleted", true), HttpStatus.OK);
    }
}
