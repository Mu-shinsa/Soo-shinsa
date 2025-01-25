package com.Soo_Shinsa.cartitem;

import com.Soo_Shinsa.cartitem.model.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Page<CartItem> findAllByUserUserId(Long userid, Pageable pageable);

    default CartItem findById(Long cartId, String exceptionMessage) {
        return findById(cartId).orElseThrow(() -> new IllegalArgumentException(exceptionMessage));
    }
}
