package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.order.OrdersResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrdersService {
    OrdersResponseDto getOrderById(Long orderId,Long userId);
    Page<OrdersResponseDto> getAllByUserId(Long userId, Pageable pageable);
    OrdersResponseDto createSingleProductOrder(Long userId, Long productId, Integer quantity);
    OrdersResponseDto createOrderFromCart(Long userId);

    OrdersResponseDto createOrder (Long userId);
}
