package com.Soo_Shinsa.controller;

import com.Soo_Shinsa.auth.UserDetailsImp;
import com.Soo_Shinsa.dto.JwtAuthResponseDto;
import com.Soo_Shinsa.dto.user.*;
import com.Soo_Shinsa.service.UserService;
import com.Soo_Shinsa.utils.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;


    @PostMapping("/signin")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody SignInRequestDto dto) {
        return ResponseEntity.ok(userService.create(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(userService.login(dto));
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


    @GetMapping
    public ResponseEntity<UserDetailResponseDto> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserDetailResponseDto userDetailResponseDto = userService.getUser(UserUtils.getUser(userDetails));
        return ResponseEntity.ok(userDetailResponseDto);
    }
    @PatchMapping
    public ResponseEntity<UserDetailResponseDto> updateUser(@AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        UserDetailResponseDto userDetailResponseDto = userService.updateUser(UserUtils.getUser(userDetails),userUpdateRequestDto);
        return ResponseEntity.ok(userDetailResponseDto);
    }

    @PostMapping("/leave")
    public ResponseEntity<Void> leave(@RequestBody LeaveRequestDto dto,
                                      @AuthenticationPrincipal UserDetailsImp authenticatedPrincipal) {
        userService.leave(dto.getPassword(),authenticatedPrincipal.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
