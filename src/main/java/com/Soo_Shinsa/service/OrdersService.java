package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.OrderItemRequestDto;
import com.Soo_Shinsa.dto.OrdersRequestDto;
import com.Soo_Shinsa.dto.OrdersResponseDto;
import com.Soo_Shinsa.model.User;

import java.util.List;

public interface OrdersService {
    OrdersResponseDto getOrderById(Long orderId, Long userId, User user);
    OrdersResponseDto createSingleProductOrder(Long userId, Long productId, Integer quantity,User user);
    OrdersResponseDto createOrderFromCart(Long userId,User user);

    OrdersResponseDto createOrder (Long userId,List<OrderItemRequestDto> orderItems);
}
