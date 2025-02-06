package com.Soo_Shinsa.user.service;

import com.Soo_Shinsa.auth.dto.JwtAuthResponseDto;
import com.Soo_Shinsa.user.dto.*;
import com.Soo_Shinsa.user.model.User;
import jakarta.validation.constraints.NotBlank;

public interface UserService {


    UserResponseDto create(SignInRequestDto dto);

    JwtAuthResponseDto login(LoginRequestDto dto);


    UserDetailResponseDto getUser(User user);

    UserDetailResponseDto updateUser(User user, UserUpdateRequestDto userUpdateRequestDto);

    void leave(@NotBlank String password, User user);
}
