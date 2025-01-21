package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.OrderItemRequestDto;
import com.Soo_Shinsa.dto.OrderItemResponseDto;
import com.Soo_Shinsa.entity.OrderItem;
import com.Soo_Shinsa.model.User;

import java.util.List;

public interface OrderItemService {
    OrderItemResponseDto createOrderItem(OrderItemRequestDto requestDto);

    OrderItem findByIdOrElseThrow(Long id);

    OrderItemResponseDto findById(Long orderItemsId);
    List<OrderItemResponseDto> findByAll(User user);

    OrderItemResponseDto update(Long orderItemsId,Integer quantity);
    OrderItemResponseDto delete(Long orderItemsId);
}
