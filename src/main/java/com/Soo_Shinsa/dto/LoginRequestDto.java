package com.Soo_Shinsa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginRequestDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
