package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.model.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private String email;
    private String phoneNum;
    private String name;
    private String status;
    private String role;
    private String userGrade;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.phoneNum = user.getPhoneNum();
        this.name = user.getName();
        this.status = user.getStatus().name();
        this.role = user.getRole().name();
        if(user.getUserGrade() != null){
            this.userGrade = user.getUserGrade().getGrade().getName();
        }
    }
}
