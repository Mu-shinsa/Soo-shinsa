package com.Soo_Shinsa.cartitem.dto;

import com.Soo_Shinsa.cartitem.model.CartItem;
import com.Soo_Shinsa.product.dto.ProductOptionResponseDto;
import com.Soo_Shinsa.product.model.ProductOption;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class CartItemResponseDto {

    private Long cartItemId;
    private Integer quantity;
    private String productName;
    private BigDecimal productPrice;
    private List<ProductOptionResponseDto> productOptions;

    public CartItemResponseDto(Long cartItemId, Integer quantity, String productName, BigDecimal productPrice, List<ProductOptionResponseDto> productOptions) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productOptions = productOptions;
    }

    public static CartItemResponseDto toDto(CartItem cartItem, List<ProductOption> productOptions) {
        List<ProductOptionResponseDto> optionDtos = productOptions.stream()
                .map(ProductOptionResponseDto::toDto)
                .toList();

        return new CartItemResponseDto(
                cartItem.getId(),
                cartItem.getQuantity(),
                cartItem.getProduct().getName(),
                cartItem.getProduct().getPrice(),
                optionDtos
        );
    }
}