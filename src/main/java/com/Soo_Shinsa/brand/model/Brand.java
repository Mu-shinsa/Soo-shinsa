package com.Soo_Shinsa.brand.model;

import com.Soo_Shinsa.constant.BaseTimeEntity;
import com.Soo_Shinsa.constant.BrandStatus;
import com.Soo_Shinsa.coupon.model.CouponBrandRelation;
import com.Soo_Shinsa.user.model.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

import static com.Soo_Shinsa.constant.BrandStatus.APPLY;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@NoArgsConstructor
public class Brand extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Length(min = 1, max = 50)
    private String registrationNum;

    @Column(nullable = false)
    @Length(min = 1, max = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String context;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BrandStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CouponBrandRelation> couponBrandRelations = new ArrayList<>();

    private Integer couponCount;
    private Boolean isCouponLimited = false;

    @Builder
    public Brand(String registrationNum, String name, String context, BrandStatus status, User user, List<CouponBrandRelation> couponBrandRelations, Integer couponCount, Boolean isCouponLimited) {
        this.registrationNum = registrationNum;
        this.name = name;
        this.context = context;
        this.status = status;
        this.user = user;
        this.couponBrandRelations = couponBrandRelations;
        this.couponCount = couponCount;
        this.isCouponLimited = isCouponLimited;
    }

    public void refuseBrand(BrandStatus status, String context) {
        this.status = status;
        this.context = context;
    }

    public void apply(BrandStatus status) {
        this.status = APPLY;
    }

    public void update(String registrationNum, String name, String context, BrandStatus status) {
        this.registrationNum = registrationNum;
        this.name = name;
        this.context = context;
        this.status = status;
    }

    // Brand.java
    public void decreaseCouponCount() {
        if (Boolean.TRUE.equals(this.isCouponLimited)) {
            if (this.couponCount == null || this.couponCount <= 0) {
                throw new IllegalStateException("발급 가능한 쿠폰이 부족합니다.");
            }
            this.couponCount--;
        }
    }
}

