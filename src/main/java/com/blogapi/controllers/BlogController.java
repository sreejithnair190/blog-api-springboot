package com.blogapi.controllers;

import com.blogapi.dto.Request.BlogRequestDto;
import com.blogapi.dto.Response.BlogResponseDto;
import com.blogapi.services.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@AllArgsConstructor
public class BlogController {

    private BlogService blogService;

    @GetMapping
    public ResponseEntity<List<BlogResponseDto>> getAllBlogs(){
        List<BlogResponseDto> blogs = blogService.getAllBlogs();
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BlogResponseDto> createBlog(@RequestBody BlogRequestDto blogRequestDto){
        BlogResponseDto blogResponseDto = blogService.createBlog(blogRequestDto);
        return new ResponseEntity<>(blogResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<BlogResponseDto> getABlog(@PathVariable Long id){
        BlogResponseDto blogResponseDto = blogService.getABlog(id);
        return new ResponseEntity<>(blogResponseDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<BlogResponseDto> updateBlog(@RequestBody BlogRequestDto blogRequestDto){
        BlogResponseDto blogResponseDto = blogService.updateBlog(blogRequestDto);
        return new ResponseEntity<>(blogResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long id){
        String message = blogService.deleteBlog(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
