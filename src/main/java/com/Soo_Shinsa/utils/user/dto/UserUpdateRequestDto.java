package com.Soo_Shinsa.utils.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserUpdateRequestDto {
    private String name;
    private String phoneNum;
    @NotBlank(message = "기존 비밀번호를 입력해주세요.")
    private String oldPassword;
    private String newPassword;
}

