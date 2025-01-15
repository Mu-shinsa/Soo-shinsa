package com.Soo_Shinsa.repository;

import com.Soo_Shinsa.entity.CartItem;
import com.Soo_Shinsa.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
