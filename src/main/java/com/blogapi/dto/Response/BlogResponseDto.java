package com.blogapi.dto.Response;

import com.blogapi.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponseDto {
    private Long id;
    private String title;
    private String content;
    private UserResponseDto user;
//    List<Comment> comments;
}
