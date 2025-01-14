package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.constant.Role;
import com.Soo_Shinsa.dto.SignInRequestDto;
import com.Soo_Shinsa.dto.UserResponseDto;
import com.Soo_Shinsa.model.Grade;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.model.UserGrade;
import com.Soo_Shinsa.repository.GradeRepository;
import com.Soo_Shinsa.repository.UserRepository;
import com.Soo_Shinsa.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;

    @Override
    public User create(SignInRequestDto dto) {
        //검증
        //중복체크
        userRepository.findByEmail(dto.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 가입된 이메일입니다.");
                });

        //user 생성
        User user = dto.toEntity();

        //customer 경우 customer grade 생성
        if (user.getRole().compareTo(Role.CUSTOMER) == 0) {
            //grade 검증
            Grade grade = gradeRepository.findByName(dto.getGrade())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 등급입니다."));

            UserGrade userGrade = new UserGrade(grade);
            user.updateUserGrade(userGrade);
        }

        //저장
        userRepository.save(user);

        return user;
    }
}
