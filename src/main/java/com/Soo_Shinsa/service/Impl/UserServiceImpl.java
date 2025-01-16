package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.dto.UserDetailResponseDto;
import com.Soo_Shinsa.dto.UserUpdateRequestDto;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.UserRepository;
import com.Soo_Shinsa.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetailResponseDto getUser(User user) {
        return new UserDetailResponseDto(user);
    }

    @Transactional
    @Override
    public UserDetailResponseDto updateUser(User user, UserUpdateRequestDto userUpdateRequestDto) {
        //user 검증
        User userById = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        if (!passwordEncoder.matches(userUpdateRequestDto.getOldPassword(), userById.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //user 업데이트
        userById.update(userUpdateRequestDto);
        userById.updatePassword(passwordEncoder.encode(userUpdateRequestDto.getNewPassword()));

        return new UserDetailResponseDto(userById);

    }
}
