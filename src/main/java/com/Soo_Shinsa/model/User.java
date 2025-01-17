package com.Soo_Shinsa.model;

import com.Soo_Shinsa.constant.Role;
import com.Soo_Shinsa.constant.UserStatus;
import com.Soo_Shinsa.dto.UserUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNum;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "user_grade_id")
    private UserGrade userGrade;

    public void updateUserGrade(UserGrade userGrade) {
        this.userGrade = userGrade;
    }

    public void delete() {
        this.status = UserStatus.DELETED;
    }

    public void update(UserUpdateRequestDto userUpdateRequestDto) {
        this.name = userUpdateRequestDto.getName();
        this.phoneNum = userUpdateRequestDto.getPhoneNum();
    }
    public void updatePassword(String password) {
        this.password = password;
    }
}