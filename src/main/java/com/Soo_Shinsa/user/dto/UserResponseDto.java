package com.Soo_Shinsa.user.dto;

import com.Soo_Shinsa.user.model.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final String email;
    private final String phoneNum;
    private final String name;
    private final String status;
    private final String role;
    private String userGrade;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.phoneNum = user.getPhoneNum();
        this.name = user.getName();
        this.status = user.getStatus().name();
        this.role = user.getRole().name();
        if(user.getUserGrade() != null){
            this.userGrade = user.getUserGrade().getGrade().getName().getName();
        }
    }
}
