package com.Soo_Shinsa.controller;

import com.Soo_Shinsa.dto.UserResponseDto;
import com.Soo_Shinsa.dto.SignInRequestDto;
import com.Soo_Shinsa.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody SignInRequestDto dto) {
        return ResponseEntity.ok(new UserResponseDto(authService.create(dto)));
    }

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Login successful");
    }
}
