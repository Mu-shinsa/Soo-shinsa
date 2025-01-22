package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.constant.AuthenticationScheme;
import com.Soo_Shinsa.constant.Role;
import com.Soo_Shinsa.constant.UserStatus;
import com.Soo_Shinsa.dto.JwtAuthResponseDto;
import com.Soo_Shinsa.dto.user.LoginRequestDto;
import com.Soo_Shinsa.dto.user.SignInRequestDto;
import com.Soo_Shinsa.dto.user.UserResponseDto;
import com.Soo_Shinsa.model.Grade;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.model.UserGrade;
import com.Soo_Shinsa.repository.GradeRepository;
import com.Soo_Shinsa.repository.UserGradeRepository;
import com.Soo_Shinsa.repository.UserRepository;
import com.Soo_Shinsa.service.AuthService;
import com.Soo_Shinsa.utils.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;
    private final UserGradeRepository userGradeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    @Override
    public UserResponseDto create(SignInRequestDto dto) {
        //검증
        //중복체크
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        //user 생성
        User user = dto.toEntity(passwordEncoder.encode(dto.getPassword()));

        //customer 경우 customer grade 생성
        if (user.getRole().equals(Role.CUSTOMER)) {
            //grade 검증
            Grade grade = gradeRepository.findByName(com.Soo_Shinsa.constant.Grade.ROOKIE.getName())
                    .orElseThrow(() -> new IllegalArgumentException("서버 오류"));

            //userGrade 생성
            UserGrade userGrade = new UserGrade(grade);

            //저장
            userGradeRepository.save(userGrade);

            user.updateUserGrade(userGrade);

        }

        //저장
        userRepository.save(user);

        return new UserResponseDto(user);
    }

    @Transactional
    @Override
    public JwtAuthResponseDto login(LoginRequestDto dto) {
        //사용자 확인
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (user.getStatus().equals(UserStatus.DELETED)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        //비밀번호 확인
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }


        //인증 객체를 저장
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        //security context에 저장
        SecurityContextHolder.getContext().setAuthentication(auth);

        //토큰 생성
        String accessToken = jwtProvider.generateToken(auth);

        return new JwtAuthResponseDto(AuthenticationScheme.BEARER.getName(), accessToken);
    }


}
