package com.Soo_Shinsa.cartitem;

import com.Soo_Shinsa.cartitem.dto.CartItemRequestDto;
import com.Soo_Shinsa.cartitem.dto.CartItemResponseDto;
import com.Soo_Shinsa.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartItemService {
    CartItemResponseDto create(User user, CartItemRequestDto requestDto);
    CartItemResponseDto findById(Long cartId, User user);
    CartItemResponseDto update(Long cartId, User user, Integer quantity);
    Page<CartItemResponseDto> findByAll(Long userId, Pageable pageable);
    void delete(Long cartId, User user);
}
