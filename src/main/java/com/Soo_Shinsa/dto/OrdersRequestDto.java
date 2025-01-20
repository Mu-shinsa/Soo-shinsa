package com.Soo_Shinsa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrdersRequestDto {
    private List<OrderItemRequestDto> orderItems; // 주문 항목 리스트

    public OrdersRequestDto(List<OrderItemRequestDto> orderItems) {
        this.orderItems = orderItems;
    }
}