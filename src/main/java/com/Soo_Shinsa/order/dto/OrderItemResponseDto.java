package com.Soo_Shinsa.order.dto;

import com.Soo_Shinsa.order.model.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class OrderItemResponseDto {

    private Long orderItemId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;

    @Builder
    public OrderItemResponseDto(Long orderItemId, Long productId, String productName, Integer quantity, BigDecimal price) {
        this.orderItemId = orderItemId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public static OrderItemResponseDto toDto(OrderItem orderItem) {
        return OrderItemResponseDto.builder()
                .orderItemId(orderItem.getId())
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProduct().getName())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getProduct().getPrice())
                .build();
    }
}
