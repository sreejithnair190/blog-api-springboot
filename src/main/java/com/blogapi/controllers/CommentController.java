package com.blogapi.controllers;

import com.blogapi.dto.Request.CommentRequestDto;
import com.blogapi.dto.Response.CommentResponseDto;
import com.blogapi.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    @GetMapping("/blog/{blogId}")
    public ResponseEntity<List<CommentResponseDto>> getAllBlogs(@PathVariable Long blogId){
        List<CommentResponseDto> comments = commentService.getAllComments(blogId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> createBlog(@RequestBody CommentRequestDto commentRequestDto){
        CommentResponseDto comment = commentService.createComment(commentRequestDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<CommentResponseDto> getABlog(@PathVariable Long id){
        CommentResponseDto comment = commentService.getAComment(id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CommentResponseDto> updateBlog(@RequestBody CommentRequestDto commentRequestDto){
        CommentResponseDto comment = commentService.updateComment(commentRequestDto);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long id){
        String message = commentService.deleteComment(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
