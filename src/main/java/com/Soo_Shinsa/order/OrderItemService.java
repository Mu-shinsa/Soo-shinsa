package com.Soo_Shinsa.order;

import com.Soo_Shinsa.order.dto.OrderItemRequestDto;
import com.Soo_Shinsa.order.dto.OrderItemResponseDto;
import com.Soo_Shinsa.order.model.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItemResponseDto createOrderItem(OrderItemRequestDto requestDto ,Long userId);

    OrderItem findByIdOrElseThrow(Long id);

    OrderItemResponseDto findById(Long orderItemsId, Long userId);
    List<OrderItemResponseDto> findByAll(Long userId);

    OrderItemResponseDto update(Long orderItemsId,Long userId,Integer quantity);
    OrderItemResponseDto delete(Long orderItemsId,Long userId);
}
