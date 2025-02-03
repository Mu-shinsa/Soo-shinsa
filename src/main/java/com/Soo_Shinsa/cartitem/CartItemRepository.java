package com.Soo_Shinsa.cartitem;

import com.Soo_Shinsa.cartitem.model.CartItem;
import com.Soo_Shinsa.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import static com.Soo_Shinsa.exception.ErrorCode.NOT_FOUND_CART;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT c FROM CartItem c WHERE c.user.userId = :userid ORDER BY c.createdAt DESC")
    Page<CartItem> findAllByUserUserId(Long userid, Pageable pageable);

    default CartItem findByIdOrElseThrow(Long cartId) {
        return findById(cartId).orElseThrow(() -> new NotFoundException(NOT_FOUND_CART));
    }

    List<CartItem> findByUserUserId(Long userId);
}