package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.OrdersResponseDto;

public interface OrdersService {
    OrdersResponseDto getOrderById(Long orderId,Long userId);
    OrdersResponseDto createSingleProductOrder(Long userId, Long productId, Integer quantity);
}
