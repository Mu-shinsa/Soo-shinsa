package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CartItemResponseDto {

    private Long id;
    private Long optionId;
    private int quantity;




    public static CartItemResponseDto toDto(CartItem cartItem) {
        return new CartItemResponseDto(
                cartItem.getId(),
                cartItem.getProductOption().getId(),
                cartItem.getQuantity()
        );
    }
}
