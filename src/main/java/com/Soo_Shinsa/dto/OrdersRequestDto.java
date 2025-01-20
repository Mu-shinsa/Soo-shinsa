package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrdersRequestDto {


    private Long userId;

    private List<OrderItemRequestDto> orderItems;

    public OrdersRequestDto(Long userId, List<OrderItemRequestDto> orderItems) {
        this.userId = userId;
        this.orderItems = orderItems;
    }
}