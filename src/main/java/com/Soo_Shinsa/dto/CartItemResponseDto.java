package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.entity.CartItem;
import lombok.Getter;

@Getter
public class CartItemResponseDto {

    private Long optionId;
    private int quantity;


    public CartItemResponseDto(Long optionId, int quantity) {
        this.optionId = optionId;
        this.quantity = quantity;
    }

    public static CartItemResponseDto toDto(CartItem cartItem) {
        return new CartItemResponseDto(
                cartItem.getCartItemId(),
                cartItem.getQuantity()
        );
    }
}
