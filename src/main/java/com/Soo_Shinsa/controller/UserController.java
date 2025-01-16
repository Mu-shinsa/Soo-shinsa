package com.Soo_Shinsa.controller;

import com.Soo_Shinsa.dto.UserDetailResponseDto;
import com.Soo_Shinsa.dto.UserUpdateRequestDto;
import com.Soo_Shinsa.service.UserService;
import com.Soo_Shinsa.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

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

}
