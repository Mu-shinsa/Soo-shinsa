package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.UserDetailResponseDto;
import com.Soo_Shinsa.dto.UserUpdateRequestDto;
import com.Soo_Shinsa.model.User;

public interface UserService {
    UserDetailResponseDto getUser(User user);

    UserDetailResponseDto updateUser(User user, UserUpdateRequestDto userUpdateRequestDto);
}
