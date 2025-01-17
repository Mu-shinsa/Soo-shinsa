package com.Soo_Shinsa.repository;

import com.Soo_Shinsa.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByUserUserId(Long id);
    List<CartItem> findByUserUserId(Long userId);
}
