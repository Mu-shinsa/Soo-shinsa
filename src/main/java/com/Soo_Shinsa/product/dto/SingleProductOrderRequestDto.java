package com.Soo_Shinsa.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SingleProductOrderRequestDto {
    @NotNull(message = "상품Id는 필수값 입니다.")
    private Long productId;
    @NotNull(message = "수량은 필수값 입니다.")
    private Integer quantity;
}