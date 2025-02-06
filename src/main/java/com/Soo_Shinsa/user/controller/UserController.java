package com.Soo_Shinsa.user.controller;

import com.Soo_Shinsa.auth.UserDetailsImp;
import com.Soo_Shinsa.auth.dto.JwtAuthResponseDto;
import com.Soo_Shinsa.user.service.UserService;
import com.Soo_Shinsa.user.dto.*;
import com.Soo_Shinsa.utils.CommonResponse;
import com.Soo_Shinsa.utils.ResponseMessage;
import com.Soo_Shinsa.utils.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
    public ResponseEntity<CommonResponse<UserResponseDto>> registerUser(@RequestBody SignInRequestDto dto) {
        UserResponseDto saved = userService.create(dto);
        CommonResponse<UserResponseDto> response = new CommonResponse<>(ResponseMessage.USER_SIGN_IN_SUCCESS, saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<JwtAuthResponseDto>> login(@RequestBody LoginRequestDto dto) {
        JwtAuthResponseDto login = userService.login(dto);
        CommonResponse<JwtAuthResponseDto> response = new CommonResponse<>(ResponseMessage.USER_LOG_IN_SUCCESS, login);
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
    public ResponseEntity<CommonResponse<UserDetailResponseDto>> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserDetailResponseDto userDetailResponseDto = userService.getUser(UserUtils.getUser(userDetails));
        CommonResponse<UserDetailResponseDto> response = new CommonResponse<>(ResponseMessage.USER_SELECT_SUCCESS,userDetailResponseDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PatchMapping
    public ResponseEntity<CommonResponse<UserDetailResponseDto>> updateUser(@AuthenticationPrincipal UserDetails userDetails,
                                                            @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        UserDetailResponseDto userDetailResponseDto = userService.updateUser(UserUtils.getUser(userDetails),userUpdateRequestDto);
        CommonResponse<UserDetailResponseDto> response = new CommonResponse<>(ResponseMessage.USER_SELECT_SUCCESS,userDetailResponseDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/leave")
    public ResponseEntity<Void> leave(@RequestBody LeaveRequestDto dto,
                                      @AuthenticationPrincipal UserDetailsImp authenticatedPrincipal) {
        userService.leave(dto.getPassword(),authenticatedPrincipal.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
