package com.Soo_Shinsa.repository;

import com.Soo_Shinsa.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
