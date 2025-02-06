package com.Soo_Shinsa.cartitem.repository;

import com.Soo_Shinsa.cartitem.model.CartItem;
import com.Soo_Shinsa.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static com.Soo_Shinsa.exception.ErrorCode.NOT_FOUND_CART;

public interface CartItemRepository extends JpaRepository<CartItem, Long>, CartItemCustomRepository {

    default CartItem findByIdOrElseThrow(Long cartId) {
        return findById(cartId).orElseThrow(() -> new NotFoundException(NOT_FOUND_CART));
    }

    List<CartItem> findByUserUserId(Long userId);
}