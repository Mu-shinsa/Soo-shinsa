package com.Soo_Shinsa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserUpdateRequestDto {
    private String name;
    private String phoneNum;
    @NotBlank
    private String oldPassword;
    private String newPassword;
}

