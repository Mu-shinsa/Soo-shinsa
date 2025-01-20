package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.OrderItemRequestDto;
import com.Soo_Shinsa.dto.OrdersRequestDto;
import com.Soo_Shinsa.dto.OrdersResponseDto;
import com.Soo_Shinsa.model.User;

import java.util.List;

public interface OrdersService {
    OrdersResponseDto getOrderById(Long orderId,Long userId);
//    OrdersResponseDto createSingleProductOrder(Long userId, Long productId, Integer quantity,User user);
    OrdersResponseDto createOrderFromCart(Long userId);

    OrdersResponseDto createOrder (Long userId);
}
