package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.OrdersResponseDto;
import com.Soo_Shinsa.model.User;

public interface OrdersService {
    OrdersResponseDto getOrderById(Long orderId, Long userId, User user);
    OrdersResponseDto createSingleProductOrder(Long userId, Long productId, Integer quantity,User user);
}
