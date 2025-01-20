package com.Soo_Shinsa.service;


import com.Soo_Shinsa.dto.CartItemResponseDto;
import com.Soo_Shinsa.dto.OrdersResponseDto;
import com.Soo_Shinsa.dto.UserDetailResponseDto;
import com.Soo_Shinsa.entity.CartItem;
import com.Soo_Shinsa.model.User;

import java.util.List;


public interface CartItemService {
    CartItemResponseDto create(Long optionId,Integer quantity,Long userId);
    CartItemResponseDto findById(Long cartId, Long userId);
    List<CartItemResponseDto> findByAll(Long userId);
    CartItem findByIdOrElseThrow(Long id);
    CartItemResponseDto update(Long cartId,Long userId,Integer quantity);

    CartItemResponseDto delete(Long cartId,Long userId);
}
