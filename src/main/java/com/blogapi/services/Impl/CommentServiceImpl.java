package com.blogapi.services.Impl;

import com.blogapi.dto.Request.CommentRequestDto;
import com.blogapi.dto.Response.CommentResponseDto;
import com.blogapi.dto.Response.UserResponseDto;
import com.blogapi.entities.Comment;
import com.blogapi.repositories.CommentRepository;
import com.blogapi.repositories.UserRepository;
import com.blogapi.services.CommentService;
import com.blogapi.services.JwtService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private JwtService jwtService;

    @Override
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        UserResponseDto userResponseDto = jwtService.extractUser();
        Comment comment = modelMapper.map(commentRequestDto, Comment.class);
        comment.setUserId(userResponseDto.getId());
        Comment newComment = commentRepository.save(comment);
        return new CommentResponseDto(newComment.getId(), newComment.getComment(), userResponseDto);
    }

    @Override
    public List<CommentResponseDto> getAllComments(Long blogId) {
        List<Comment> comments = commentRepository.findByBlogId(blogId);
        return comments.stream()
                .map(comment -> new CommentResponseDto(
                        comment.getId(),
                        comment.getComment(),
                        modelMapper.map(
                                userRepository.findById(comment.getUserId()),
                                UserResponseDto.class
                        )
                ))
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponseDto getAComment(Long id) {
        Comment comment = commentRepository.findById(id).get();
        UserResponseDto userResponseDto = jwtService.extractUser();
        return new CommentResponseDto(comment.getId(), comment.getComment(), userResponseDto);
    }

    @Override
    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentRequestDto.getId()).get();
        UserResponseDto userResponseDto = jwtService.extractUser();

        if (!Objects.equals(userResponseDto.getId(), comment.getUserId())){
            throw new RuntimeException("Comment cannot be updated");
        }

        comment.setComment(commentRequestDto.getComment());

        Comment newComment = commentRepository.save(comment);
        return new CommentResponseDto(newComment.getId(), newComment.getComment(), userResponseDto);
    }

    @Override
    public String deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).get();
        UserResponseDto userResponseDto = jwtService.extractUser();

        if (!Objects.equals(userResponseDto.getId(), comment.getUserId())){
            throw new RuntimeException("Comment cannot be deleted");
        }

        commentRepository.delete(comment);
        return "Comment Deleted Successfully";
    }
}
