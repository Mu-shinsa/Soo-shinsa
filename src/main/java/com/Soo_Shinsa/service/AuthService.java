package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.JwtAuthResponseDto;
import com.Soo_Shinsa.dto.LoginRequestDto;
import com.Soo_Shinsa.dto.SignInRequestDto;
import com.Soo_Shinsa.dto.UserResponseDto;

public interface AuthService {
    UserResponseDto create(SignInRequestDto dto);

    JwtAuthResponseDto login(LoginRequestDto dto);
}
