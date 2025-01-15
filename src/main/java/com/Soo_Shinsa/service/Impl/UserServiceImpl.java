package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.dto.UserDetailResponseDto;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.UserRepository;
import com.Soo_Shinsa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetailResponseDto getUser(User user) {
        return new UserDetailResponseDto(user);
    }
}
