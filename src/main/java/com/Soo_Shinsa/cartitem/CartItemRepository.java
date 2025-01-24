package com.Soo_Shinsa.cartitem;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByUserUserId(Long userid);
    List<CartItem> findByUserUserId(Long userId);
}
