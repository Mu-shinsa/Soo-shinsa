package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.JwtAuthResponseDto;
import com.Soo_Shinsa.dto.LoginRequestDto;
import com.Soo_Shinsa.dto.SignInRequestDto;
import com.Soo_Shinsa.dto.UserResponseDto;
import com.Soo_Shinsa.model.User;
import jakarta.validation.constraints.NotBlank;

public interface AuthService {
    UserResponseDto create(SignInRequestDto dto);

    JwtAuthResponseDto login(LoginRequestDto dto);

    void leave(String password, User user);
}
