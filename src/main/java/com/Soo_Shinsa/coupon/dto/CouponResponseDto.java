package com.Soo_Shinsa.coupon.dto;

import com.Soo_Shinsa.constant.CouponType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class CouponResponseDto {
    private Long id;
    private String couponName;
    private String couponCode;
    private BigDecimal discountRate;
    private LocalDate expirationDate;
    private LocalDate issueDate;
    private CouponType couponType;
    private Integer maxCount;
    private List<CouponBrandRelationDto> brandRelations;

    @Builder
    public CouponResponseDto(Long id, String couponName, String couponCode, BigDecimal discountRate, LocalDate expirationDate, LocalDate issueDate, Integer maxCount, CouponType couponType, List<CouponBrandRelationDto> brandRelations) {
        this.id = id;
        this.couponName = couponName;
        this.couponCode = couponCode;
        this.discountRate = discountRate;
        this.expirationDate = expirationDate;
        this.issueDate = issueDate;
        this.couponType = couponType;
        this.brandRelations = brandRelations;
        this.maxCount = maxCount;
    }
}
