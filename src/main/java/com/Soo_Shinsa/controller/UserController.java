package com.Soo_Shinsa.controller;

import com.Soo_Shinsa.auth.UserDetailsImp;
import com.Soo_Shinsa.dto.UserDetailResponseDto;
import com.Soo_Shinsa.dto.UserResponseDto;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.service.UserService;
import com.Soo_Shinsa.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    

}
