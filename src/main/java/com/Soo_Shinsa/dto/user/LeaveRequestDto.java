package com.Soo_Shinsa.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LeaveRequestDto {
    @NotBlank
    private String password;
}
