package com.Soo_Shinsa.coupon.model;

import com.Soo_Shinsa.brand.model.Brand;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CouponBrandRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Builder
    public CouponBrandRelation(Coupon coupon, Brand brand) {
        this.coupon = coupon;
        this.brand = brand;
    }
}

