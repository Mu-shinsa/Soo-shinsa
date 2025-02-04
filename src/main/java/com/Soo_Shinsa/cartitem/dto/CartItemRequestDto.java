package com.Soo_Shinsa.cartitem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemRequestDto {

    @NotNull(message = "상품 아이디를 입력해주세요.")
    private Long productId;

    @NotNull(message = "수량을 입력해주세요.")
    private Integer quantity;

    @NotNull(message = "상품 옵션 아이디를 입력해주세요.")
    private Long productOptionId;

    public CartItemRequestDto(Long productId, Integer quantity, Long productOptionId) {
        this.productId = productId;
        this.quantity = quantity;
        this.productOptionId = productOptionId;
    }
}