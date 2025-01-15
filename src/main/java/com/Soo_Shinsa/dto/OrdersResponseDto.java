package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.constant.Status;
import com.Soo_Shinsa.entity.OrderItem;
import com.Soo_Shinsa.entity.Orders;
import lombok.Getter;

import java.util.List;

@Getter
public class OrdersResponseDto {
    private Long id;
    private Long userId;
    private String orderNumber;
    private double totalPrice;
    private Status status;
    private List<OrderItem> orderItems;

    public OrdersResponseDto(Long id, Long userId, String orderNumber, double totalPrice, Status status, List<OrderItem> orderItems) {
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
                orders.getOrderItems()

        );
    }
}
