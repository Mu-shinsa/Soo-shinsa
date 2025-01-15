package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.OrdersResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface OrdersService {
    OrdersResponseDto getOrderById(Long orderId,Long userId);
}
