package com.blogapi.controllers;

import com.blogapi.dto.AuthResponseDto;
import com.blogapi.dto.UserDto;
import com.blogapi.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @PostMapping("sign-up")
    public ResponseEntity<AuthResponseDto> signUp(@RequestBody UserDto userDto){
        AuthResponseDto authResponseDto = userService.createUser(userDto);
        return new ResponseEntity<>(authResponseDto, HttpStatus.CREATED);
    }
}
