package com.Soo_Shinsa.order.repository;

import com.Soo_Shinsa.order.dto.OrderDateRequestDto;
import com.Soo_Shinsa.order.dto.OrderItemResponseDto;
import com.Soo_Shinsa.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderItemCustomRepository {
    Page<OrderItemResponseDto> findByAll(User user, OrderDateRequestDto dateRequestDto, Pageable pageable);
}
