package com.Soo_Shinsa.coupon.calculate;

import com.Soo_Shinsa.coupon.model.Coupon;
import com.Soo_Shinsa.product.model.Product;

import java.math.BigDecimal;

public interface DiscountCouponCalculator {
    BigDecimal calculateDiscountedPrice(BigDecimal originalPrice, BigDecimal discountRate);
    BigDecimal applyCoupon(Coupon coupon, Product product, BigDecimal originalPrice);
}
