package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.model.Grade;
import com.Soo_Shinsa.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDetailResponseDto {
    private final String name;
    private final String phoneNum;
    private final String role;
    private String userGrade;
    private String pointRate;
    private String requirement;

    public UserDetailResponseDto(User user) {
        this.name = user.getName();
        this.phoneNum = user.getPhoneNum();
        this.role = user.getRole().name();
        if(user.getUserGrade() != null){
            Grade grade = user.getUserGrade().getGrade();
            this.userGrade = grade.getName();
            this.pointRate = grade.getPointRate().toString();
            this.requirement = grade.getRequirement().toString();

        }
    }
}
