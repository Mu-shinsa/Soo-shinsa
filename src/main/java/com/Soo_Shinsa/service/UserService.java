package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.JwtAuthResponseDto;
import com.Soo_Shinsa.dto.user.*;
import com.Soo_Shinsa.model.User;
import jakarta.validation.constraints.NotBlank;

public interface UserService {


    UserResponseDto create(SignInRequestDto dto);

    JwtAuthResponseDto login(LoginRequestDto dto);


    UserDetailResponseDto getUser(User user);

    UserDetailResponseDto updateUser(User user, UserUpdateRequestDto userUpdateRequestDto);

    void leave(@NotBlank String password, User user);
}
