package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.JwtAuthResponseDto;
import com.Soo_Shinsa.dto.user.LoginRequestDto;
import com.Soo_Shinsa.dto.user.SignInRequestDto;
import com.Soo_Shinsa.dto.user.UserResponseDto;

public interface AuthService {
    UserResponseDto create(SignInRequestDto dto);

    JwtAuthResponseDto login(LoginRequestDto dto);

}
