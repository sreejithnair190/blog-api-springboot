package com.blogapi.controllers;

import com.blogapi.dto.Response.AuthResponseDto;
import com.blogapi.dto.Request.UserRequestDto;
import com.blogapi.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @PostMapping("sign-up")
    public ResponseEntity<AuthResponseDto> signUp(@RequestBody UserRequestDto userRequestDto){
        AuthResponseDto authResponseDto = userService.createUser(userRequestDto);
        return new ResponseEntity<>(authResponseDto, HttpStatus.CREATED);
    }
}
