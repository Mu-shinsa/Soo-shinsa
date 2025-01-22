package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.user.UserDetailResponseDto;
import com.Soo_Shinsa.dto.user.UserUpdateRequestDto;
import com.Soo_Shinsa.model.User;
import jakarta.validation.constraints.NotBlank;

public interface UserService {
    UserDetailResponseDto getUser(User user);

    UserDetailResponseDto updateUser(User user, UserUpdateRequestDto userUpdateRequestDto);

    void leave(@NotBlank String password, User user);
}
