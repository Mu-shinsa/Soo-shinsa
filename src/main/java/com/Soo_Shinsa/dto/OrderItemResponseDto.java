package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.entity.CartItem;
import com.Soo_Shinsa.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderItemResponseDto {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;



    public static OrderItemResponseDto toDto(OrderItem orderItem) {
        return new OrderItemResponseDto(
                orderItem.getId(),
                orderItem.getOrder().getId(),
                orderItem.getProduct().getId(),
                orderItem.getQuantity()

        );
    }
}
