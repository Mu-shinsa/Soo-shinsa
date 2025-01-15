package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.entity.CartItem;
import com.Soo_Shinsa.entity.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemResponseDto {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;

    public OrderItemResponseDto(Long id, Long orderId, Long productId, Integer quantity) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static OrderItemResponseDto toDto(OrderItem orderItem) {
        return new OrderItemResponseDto(
                orderItem.getId(),
                orderItem.getOrder().getId(),
                orderItem.getProduct().getId(),
                orderItem.getQuantity()

        );
    }
}
