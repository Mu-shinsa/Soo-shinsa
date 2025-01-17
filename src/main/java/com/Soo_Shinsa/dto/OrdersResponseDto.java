package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.constant.Status;
import com.Soo_Shinsa.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrdersResponseDto {
    private Long id;
    private Long userId;
    private String orderNumber;
    private BigDecimal totalPrice;
    private Status status;
    private List<OrderItemResponseDto> orderItems;



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
