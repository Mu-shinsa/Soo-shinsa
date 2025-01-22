package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.constant.OrdersStatus;
import com.Soo_Shinsa.entity.Orders;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
@NoArgsConstructor
@Getter
public class OrdersResponseDto {
    private Long id;

    private String orderNumber;

    private BigDecimal totalPrice;

    private OrdersStatus status;

    private Long userId;

    private List<OrderItemResponseDto> orderItems;

    public OrdersResponseDto(Long id, String orderNumber, BigDecimal totalPrice, OrdersStatus status, Long userId, List<OrderItemResponseDto> orderItems) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.totalPrice = totalPrice;
        this.status = status;
        this.userId = userId;
        this.orderItems = orderItems;
    }

    public static OrdersResponseDto toDto(Orders orders) {
        return new OrdersResponseDto(
                orders.getId(),
                orders.getOrderNumber(),
                orders.getTotalPrice(),
                orders.getStatus(),
                orders.getUser().getUserId(),
                orders.getOrderItems().stream()
                        .map(OrderItemResponseDto::toDto) // OrderItemResponseDto의 toDto 사용
                        .collect(Collectors.toList())
        );
    }

}
