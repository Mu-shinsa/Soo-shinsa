package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.OrderItemResponseDto;
import com.Soo_Shinsa.entity.OrderItem;
import com.Soo_Shinsa.model.User;

import java.util.List;

public interface OrderItemService {
    OrderItemResponseDto createOrderItem(Long orderId, Long productId, Integer quantity, Long userId, User user);

    OrderItem findByIdOrElseThrow(Long id);

    OrderItemResponseDto findById(Long orderItemsId, Long userId,User user);
    List<OrderItemResponseDto> findByAll(Long userId,User user);

    OrderItemResponseDto update(Long orderItemsId,Long userId,Integer quantity,User user);
    OrderItemResponseDto delete(Long orderItemsId,Long userId,User user);
}
