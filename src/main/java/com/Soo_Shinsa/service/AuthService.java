package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.SignInRequestDto;
import com.Soo_Shinsa.dto.UserResponseDto;
import com.Soo_Shinsa.model.User;

public interface AuthService {
    User create(SignInRequestDto dto);
}
