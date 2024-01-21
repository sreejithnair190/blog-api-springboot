package com.blogapi.services.Impl;

import com.blogapi.dto.AuthResponseDto;
import com.blogapi.dto.UserDto;
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
    public AuthResponseDto createUser(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()){
            throw new ValueExistException("Email Already Exist");
        }

        userDto.setPassword(utils.encodePassword(userDto.getPassword()));
        User user = modelMapper.map(userDto, User.class);

        User newUser = userRepository.save(user);

        String token = jwtService.generateToken(newUser);

        return new AuthResponseDto(
                newUser.getId(), newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), token
        );
    }
}
