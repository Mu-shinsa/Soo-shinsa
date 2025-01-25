package com.Soo_Shinsa.user.model;

import com.Soo_Shinsa.cartitem.model.CartItem;
import com.Soo_Shinsa.constant.Role;
import com.Soo_Shinsa.constant.UserStatus;

import com.Soo_Shinsa.user.dto.UserUpdateRequestDto;

import com.Soo_Shinsa.report.model.Report;
import com.Soo_Shinsa.review.model.Review;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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

    @Builder
    public User(String email, String password, String name, String phoneNum, UserStatus status, Role role, UserGrade userGrade) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNum = phoneNum;
        this.status = status;
        this.role = role;
        this.userGrade = userGrade;
    }

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

    public void validateReviewUser(Review review) {
        if (!review.getUser().getUserId().equals(this.userId)) {
            throw new IllegalArgumentException("본인의 리뷰만 수정/삭제 가능합니다.");
        }
    }

    public void validateCartItemUser(CartItem cartItem) {
        if (!cartItem.getUser().getUserId().equals(this.userId)) {
            throw new IllegalArgumentException("본인의 장바구니만 추가/수정/삭제 가능합니다.");
        }
    }

    public void validateAdminRole () {
        if ((!Role.ADMIN.equals(this.role))) {
            throw new IllegalArgumentException("관리자만 접근 가능합니다.");
        }
    }

    /**
     * 관리자(Admin) 또는 판매자(Vendor) 권한 검증
     */
    public void validateAdminOrVendorRole() {
        if (!Role.ADMIN.equals(this.role) && !Role.VENDOR.equals(this.role)) {
            throw new IllegalArgumentException("관리자 또는 판매자만 접근 가능합니다.");
        }
    }

    public void validateReportUser(Report report) {
        if (!report.getUser().getUserId().equals(this.userId)) {
            throw new IllegalArgumentException("본인의 신고만 조회/처리 가능합니다.");
        }
    }
}