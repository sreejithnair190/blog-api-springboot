package com.blogapi.services;

import com.blogapi.dto.Request.CommentRequestDto;
import com.blogapi.dto.Response.CommentResponseDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(CommentRequestDto commentRequestDto);
    List<CommentResponseDto> getAllComments(Long blogId);
    CommentResponseDto getAComment(Long id);
    CommentResponseDto updateComment(CommentRequestDto commentRequestDto);
    String deleteComment(Long id);
}
