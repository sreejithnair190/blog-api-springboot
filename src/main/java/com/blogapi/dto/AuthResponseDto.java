package com.blogapi.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String token;
}
