package com.Soo_Shinsa.service;


import com.Soo_Shinsa.dto.CartItemResponseDto;
import com.Soo_Shinsa.dto.OrdersResponseDto;
import com.Soo_Shinsa.dto.UserDetailResponseDto;
import com.Soo_Shinsa.entity.CartItem;
import com.Soo_Shinsa.model.User;

import java.util.List;


public interface CartItemService {
    CartItemResponseDto create(Long optionId,Integer quantity,Long userId,User user);
    CartItemResponseDto findById(Long cartId, Long userId, User user);
    List<CartItemResponseDto> findByAll(Long userId,User user);
    CartItem findByIdOrElseThrow(Long id);
    CartItemResponseDto update(Long cartId,Long userId,Integer quantity,User uer);

    CartItemResponseDto delete(Long cartId,Long userId,User user);
}
