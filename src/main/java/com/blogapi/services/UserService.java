package com.blogapi.services;

import com.blogapi.dto.Response.AuthResponseDto;
import com.blogapi.dto.Request.UserRequestDto;

public interface UserService {

    AuthResponseDto createUser(UserRequestDto userRequestDto);
}
