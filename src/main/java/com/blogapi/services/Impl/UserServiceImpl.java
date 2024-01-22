package com.blogapi.services.Impl;

import com.blogapi.dto.Response.AuthResponseDto;
import com.blogapi.dto.Request.UserRequestDto;
import com.blogapi.dto.Response.UserResponseDto;
import com.blogapi.entities.User;
import com.blogapi.exceptions.ValueExistException;
import com.blogapi.repositories.UserRepository;
import com.blogapi.services.JwtService;
import com.blogapi.services.UserService;
import com.blogapi.utils.Utils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private Utils utils;
    private JwtService jwtService;

    @Override
    public AuthResponseDto createUser(UserRequestDto userRequestDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userRequestDto.getEmail());
        if (optionalUser.isPresent()){
            throw new ValueExistException("Email Already Exist");
        }

        userRequestDto.setPassword(utils.encodePassword(userRequestDto.getPassword()));
        User user = modelMapper.map(userRequestDto, User.class);

        User newUser = userRepository.save(user);

        String token = jwtService.generateToken(newUser);
        UserResponseDto userResponseDto = new UserResponseDto(
                newUser.getId(), newUser.getFirstName(), newUser.getLastName(), newUser.getEmail()
        );
        return new AuthResponseDto(
               userResponseDto , token
        );
    }
}
