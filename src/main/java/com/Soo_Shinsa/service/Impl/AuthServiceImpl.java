package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.constant.AuthenticationScheme;
import com.Soo_Shinsa.constant.Role;
import com.Soo_Shinsa.constant.UserStatus;
import com.Soo_Shinsa.dto.JwtAuthResponseDto;
import com.Soo_Shinsa.dto.LoginRequestDto;
import com.Soo_Shinsa.dto.SignInRequestDto;
import com.Soo_Shinsa.dto.UserResponseDto;
import com.Soo_Shinsa.model.Grade;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.model.UserGrade;
import com.Soo_Shinsa.repository.GradeRepository;
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
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    @Override
    public UserResponseDto create(SignInRequestDto dto) {
        //검증
        //중복체크
        userRepository.findByEmail(dto.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("가입이 불가능한 이메일입니다.");
                });
         
        //user 생성
        User user = dto.toEntity(passwordEncoder.encode(dto.getPassword()));

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

        return new UserResponseDto(user);
    }

    @Transactional
    @Override
    public JwtAuthResponseDto login(LoginRequestDto dto) {
        //사용자 확인
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(user.getStatus().compareTo(UserStatus.DELETED) == 0){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        //비밀번호 확인
        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
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

    @Transactional
    @Override
    public void leave(String password, User user) {
        //비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        //탈퇴
        user.delete();
    }
}
