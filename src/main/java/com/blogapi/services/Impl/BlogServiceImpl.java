package com.blogapi.services.Impl;

import com.blogapi.dto.Request.BlogRequestDto;
import com.blogapi.dto.Response.BlogResponseDto;
import com.blogapi.dto.Response.UserResponseDto;
import com.blogapi.entities.Blog;
import com.blogapi.repositories.BlogRepository;
import com.blogapi.repositories.UserRepository;
import com.blogapi.services.BlogService;
import com.blogapi.services.JwtService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

    private BlogRepository blogRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private JwtService jwtService;

    @Override
    public BlogResponseDto createBlog(BlogRequestDto blogRequestDto) {
        UserResponseDto userResponseDto = jwtService.extractUser();
        Blog blog = modelMapper.map(blogRequestDto, Blog.class);
        blog.setUserId(userResponseDto.getId());
        Blog newBlog = blogRepository.save(blog);
        return new BlogResponseDto(
                newBlog.getId(), newBlog.getTitle(), newBlog.getContent(), userResponseDto
        );
    }

    @Override
    public List<BlogResponseDto> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        return blogs.stream()
                .map(blog -> new BlogResponseDto(
                        blog.getId(),
                        blog.getTitle(),
                        blog.getContent(),
                        modelMapper.map(
                                userRepository.findById(blog.getUserId()),
                                UserResponseDto.class
                        )
                ))
                .collect(Collectors.toList());
    }

    @Override
    public BlogResponseDto getABlog(Long id) {
        Blog blog = blogRepository.findById(id).get();
        UserResponseDto userResponseDto = jwtService.extractUser();
        return new BlogResponseDto(blog.getId(), blog.getTitle(), blog.getContent(), userResponseDto);
    }

    @Override
    public BlogResponseDto updateBlog(BlogRequestDto blogRequestDto) {
        Blog blog = blogRepository.findById(blogRequestDto.getId()).get();
        UserResponseDto userResponseDto = jwtService.extractUser();

        if (!Objects.equals(userResponseDto.getId(), blog.getUserId())){
            throw new RuntimeException("Blog cannot be updated");
        }

        blog.setTitle(blogRequestDto.getTitle());
        blog.setContent(blogRequestDto.getContent());

        Blog updatedBlog = blogRepository.save(blog);

        return new BlogResponseDto(updatedBlog.getId(), updatedBlog.getTitle(), updatedBlog.getContent(), userResponseDto);
    }

    @Override
    public String deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id).get();
        UserResponseDto userResponseDto = jwtService.extractUser();

        if (!Objects.equals(userResponseDto.getId(), blog.getUserId())){
            throw new RuntimeException("Blog cannot be deleted");
        }
        blogRepository.delete(blog);
        return "Blog Deleted Successfully";
    }
}
