package com.blogapi.services;

import com.blogapi.dto.AuthResponseDto;
import com.blogapi.dto.UserDto;

public interface UserService {

    AuthResponseDto createUser(UserDto userDto);
}
