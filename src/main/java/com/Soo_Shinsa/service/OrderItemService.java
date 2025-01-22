package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.order.OrderItemRequestDto;
import com.Soo_Shinsa.dto.order.OrderItemResponseDto;
import com.Soo_Shinsa.model.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItemResponseDto createOrderItem(OrderItemRequestDto requestDto ,Long userId);

    OrderItem findByIdOrElseThrow(Long id);

    OrderItemResponseDto findById(Long orderItemsId, Long userId);
    List<OrderItemResponseDto> findByAll(Long userId);

    OrderItemResponseDto update(Long orderItemsId,Long userId,Integer quantity);
    OrderItemResponseDto delete(Long orderItemsId,Long userId);
}
