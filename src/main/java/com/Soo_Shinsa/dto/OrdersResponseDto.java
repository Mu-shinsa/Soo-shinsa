package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.constant.Status;
import com.Soo_Shinsa.entity.Orders;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrdersResponseDto {
    private Long id;
    private Long userId;
    private String orderNumber;
    private BigDecimal totalPrice;
    private Status status;
    private List<OrderItemResponseDto> orderItems;

    public OrdersResponseDto(Long id, Long userId, String orderNumber, BigDecimal totalPrice, Status status, List<OrderItemResponseDto> orderItems) {
        this.id = id;
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderItems = orderItems;
    }

    public static OrdersResponseDto toDto(Orders orders) {
        return new OrdersResponseDto(
                orders.getId(),
                orders.getUser().getUserId(),
                orders.getOrderNumber(),
                orders.getTotalPrice(),
                orders.getStatus(),
                orders.getOrderItems().stream()
                        .map(OrderItemResponseDto::toDto) // OrderItemResponseDto의 toDto 사용
                        .collect(Collectors.toList())
        );
    }

}
