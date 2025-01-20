package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor

@Getter
public class CartItemResponseDto {

    private Long cartItemId;       // 카트 아이템 ID
    private Integer quantity;      // 수량
    private String productName;    // 상품명
    private BigDecimal productPrice; // 상품 가격
    private String productStatus;  // 상품 상태
    private String optionSize;     // 옵션 사이즈
    private String optionColor;    // 옵션 색상
    private String optionStatus;   // 옵션 상태

    public CartItemResponseDto(Long cartItemId, Integer quantity, String productName, BigDecimal productPrice, String productStatus, String optionSize, String optionColor, String optionStatus) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStatus = productStatus;
        this.optionSize = optionSize;
        this.optionColor = optionColor;
        this.optionStatus = optionStatus;
    }

    // 엔티티 -> DTO 변환 메서드
    public static CartItemResponseDto toDto(CartItem cartItem) {
        return new CartItemResponseDto(
                cartItem.getId(),
                cartItem.getQuantity(),
                cartItem.getProductOption().getProduct().getName(), // 상품명
                cartItem.getProductOption().getProduct().getPrice(), // 상품 가격
                cartItem.getProductOption().getProduct().getStatus(), // 상품 상태
                cartItem.getProductOption().getSize(), // 옵션 사이즈
                cartItem.getProductOption().getColor(), // 옵션 색상
                cartItem.getProductOption().getStatus() // 옵션 상태
        );
    }
}