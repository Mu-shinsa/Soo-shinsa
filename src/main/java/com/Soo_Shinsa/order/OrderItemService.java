package com.Soo_Shinsa.order;



import com.Soo_Shinsa.order.dto.OrderItemRequestDto;
import com.Soo_Shinsa.order.dto.OrderItemResponseDto;
import com.Soo_Shinsa.order.model.OrderItem;
import com.Soo_Shinsa.user.model.User;

import java.util.List;

public interface OrderItemService {
    OrderItemResponseDto createOrderItem(OrderItemRequestDto requestDto);

    OrderItem findByIdOrElseThrow(Long id);

    OrderItemResponseDto findById(Long orderItemsId);
    List<OrderItemResponseDto> findByAll(User user);

    OrderItemResponseDto update(Long orderItemsId,Integer quantity,User user);
    OrderItemResponseDto delete(Long orderItemsId,User user);
}
