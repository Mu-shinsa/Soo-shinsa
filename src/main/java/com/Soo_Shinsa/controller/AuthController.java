package com.Soo_Shinsa.controller;

import com.Soo_Shinsa.dto.*;
import com.Soo_Shinsa.dto.user.LoginRequestDto;
import com.Soo_Shinsa.dto.user.SignInRequestDto;
import com.Soo_Shinsa.dto.user.UserResponseDto;
import com.Soo_Shinsa.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody SignInRequestDto dto) {
        return ResponseEntity.ok(authService.create(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws UsernameNotFoundException {
        // 인증 정보가 있다면 로그아웃 처리.
        if (authentication != null && authentication.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        // 인증 정보가 없다면 인증되지 않았기 때문에 로그인 필요.
        throw new UsernameNotFoundException("로그인이 먼저 필요합니다.");
    }


}
