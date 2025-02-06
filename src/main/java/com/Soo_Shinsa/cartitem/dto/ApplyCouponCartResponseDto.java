package com.Soo_Shinsa.cartitem.dto;

import com.Soo_Shinsa.cartitem.model.CartItem;
import com.Soo_Shinsa.product.dto.ProductOptionResponseDto;
import com.Soo_Shinsa.product.model.ProductOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ApplyCouponCartResponseDto {

    private Long cartItemId;
    private Long productId;
    private BigDecimal originalPrice;
    private BigDecimal discountedPrice; // 추가
    private List<ProductOptionResponseDto> productOptions;

    @Builder
    public ApplyCouponCartResponseDto(Long cartItemId, Long productId, BigDecimal originalPrice, BigDecimal discountedPrice, List<ProductOptionResponseDto> productOptions) {
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.originalPrice = originalPrice;
        this.discountedPrice = discountedPrice;
        this.productOptions = productOptions;
    }

    public static ApplyCouponCartResponseDto toDto(CartItem cartItem, List<ProductOption> productOptions) {
        return ApplyCouponCartResponseDto.builder()
                .cartItemId(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .originalPrice(cartItem.getProduct().getPrice())
                .discountedPrice(cartItem.getDiscountedPrice()) // 할인된 금액 설정
                .productOptions(productOptions.stream()
                        .map(ProductOptionResponseDto::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
