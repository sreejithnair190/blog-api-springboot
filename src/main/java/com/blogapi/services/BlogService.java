package com.blogapi.services;

import com.blogapi.dto.Request.BlogRequestDto;
import com.blogapi.dto.Response.BlogResponseDto;

import java.util.List;

public interface BlogService {
    BlogResponseDto createBlog(BlogRequestDto blogRequestDto);
    List<BlogResponseDto> getAllBlogs();
    BlogResponseDto getABlog(Long id);
    BlogResponseDto updateBlog(BlogRequestDto blogRequestDto);
    String deleteBlog(Long id);
}
