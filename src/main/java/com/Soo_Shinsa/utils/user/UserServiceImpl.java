package com.Soo_Shinsa.utils.user;

import com.Soo_Shinsa.constant.AuthenticationScheme;
import com.Soo_Shinsa.constant.Role;
import com.Soo_Shinsa.constant.UserStatus;
import com.Soo_Shinsa.auth.dto.JwtAuthResponseDto;

import com.Soo_Shinsa.utils.user.dto.*;
import com.Soo_Shinsa.utils.user.model.Grade;
import com.Soo_Shinsa.utils.user.model.User;
import com.Soo_Shinsa.utils.user.model.UserGrade;
import com.Soo_Shinsa.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final GradeRepository gradeRepository;
    private final UserGradeRepository userGradeRepository;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
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
            user.updateUserGrade(createNewUserGrade());
        }

        //저장
        userRepository.save(user);

        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
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

    @Transactional
    @Override
    public void leave(String password, User user) {
        //비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        //탈퇴
        user.delete();
    }




    private UserGrade createNewUserGrade() {
        //grade 검증
        Grade grade = gradeRepository.findByName(com.Soo_Shinsa.constant.Grade.ROOKIE.getName())
                .orElseThrow(() -> new IllegalArgumentException("서버 오류"));

        //userGrade 생성
        UserGrade userGrade = new UserGrade(grade);

        //저장
        userGradeRepository.save(userGrade);
        return userGrade;
    }
}
