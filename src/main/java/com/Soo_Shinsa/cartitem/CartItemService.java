package com.Soo_Shinsa.cartitem;


import com.Soo_Shinsa.cartitem.dto.CartItemResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CartItemService {
    CartItemResponseDto create(Long optionId,Integer quantity,Long userId);
    CartItemResponseDto findById(Long cartId, Long userId);
    Page<CartItemResponseDto> findByAll(Long userId, Pageable pageable);
    CartItem findByIdOrElseThrow(Long id);
    CartItemResponseDto update(Long cartId,Long userId,Integer quantity);

    CartItemResponseDto delete(Long cartId,Long userId);
}
