package com.Soo_Shinsa.service;


import com.Soo_Shinsa.dto.CartItemResponseDto;

import com.Soo_Shinsa.entity.CartItem;
import com.Soo_Shinsa.model.User;

import java.util.List;


public interface CartItemService {
    CartItemResponseDto create(Long optionId, Integer quantity, User user);
    CartItemResponseDto findById(Long cartId);
    List<CartItemResponseDto> findByAll(User user);
    CartItem findByIdOrElseThrow(Long id);
    CartItemResponseDto update(Long cartId,Integer quantity);

    CartItemResponseDto delete(Long cartId);
}
