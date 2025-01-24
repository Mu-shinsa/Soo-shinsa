package com.Soo_Shinsa.order;

import com.Soo_Shinsa.order.dto.OrdersResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrdersService {
    OrdersResponseDto getOrderById(Long orderId,Long userId);
    Page<OrdersResponseDto> getAllByUserId(Long userId, Pageable pageable);
    OrdersResponseDto createSingleProductOrder(Long userId, Long productId, Integer quantity);
    OrdersResponseDto createOrderFromCart(Long userId);

    OrdersResponseDto createOrder (Long userId);
}
