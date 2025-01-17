package com.Soo_Shinsa.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderItemRequestDto {
    @NotNull(message = "주문Id는 필수값 입니다.")
    private Long orderId;

    @NotNull(message = "상품Id는 필수값 입니다.")
    private Long productId;

    @NotNull(message = "수량은 필수값 입니다.")
    private Integer quantity;

    public OrderItemRequestDto(Long orderId, Long productId, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
