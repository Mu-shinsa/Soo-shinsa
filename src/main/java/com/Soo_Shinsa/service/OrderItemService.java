package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.OrderItemResponseDto;
import com.Soo_Shinsa.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItemResponseDto createOrderItem(Long orderId,Long productId, Integer quantity,Long userId);

    OrderItem findByIdOrElseThrow(Long id);

    OrderItemResponseDto findById(Long orderItemsId, Long userId);
    List<OrderItemResponseDto> findByAll(Long userId);

    OrderItemResponseDto update(Long orderItemsId,Long userId,Integer quantity);
    void delete(Long orderItemsId,Long userId);
}
