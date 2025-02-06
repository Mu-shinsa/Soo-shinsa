package com.Soo_Shinsa.cartitem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplyCouponCartRequestDto {
    private Long couponId;

    public ApplyCouponCartRequestDto(Long couponId) {
        this.couponId = couponId;
    }
}
