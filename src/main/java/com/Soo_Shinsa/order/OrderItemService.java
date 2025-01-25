package com.Soo_Shinsa.order;



import com.Soo_Shinsa.order.dto.OrderItemRequestDto;
import com.Soo_Shinsa.order.dto.OrderItemResponseDto;
import com.Soo_Shinsa.order.model.OrderItem;
import com.Soo_Shinsa.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderItemService {
    OrderItemResponseDto createOrderItem(OrderItemRequestDto requestDto,User user);

    OrderItem findByIdOrElseThrow(Long id);

    OrderItemResponseDto findById(Long orderItemsId,User user);
    Page<OrderItemResponseDto> findByAll(User user, Pageable pageable);

    OrderItemResponseDto update(Long orderItemsId,Integer quantity,User user);
    OrderItemResponseDto delete(Long orderItemsId,User user);
}
