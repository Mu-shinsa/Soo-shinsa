package com.Soo_Shinsa.cartitem.repository;

import com.Soo_Shinsa.cartitem.dto.CartItemDateRequestDto;
import com.Soo_Shinsa.cartitem.dto.CartItemResponseDto;
import com.Soo_Shinsa.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartItemCustomRepository {
    Page<CartItemResponseDto> findByAllCartItem(User user, CartItemDateRequestDto requestDto, Pageable pageable);
}
