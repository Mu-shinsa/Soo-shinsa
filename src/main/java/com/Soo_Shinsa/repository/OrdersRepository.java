package com.Soo_Shinsa.repository;

import com.Soo_Shinsa.entity.CartItem;
import com.Soo_Shinsa.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    @Query("SELECT o FROM Orders o JOIN FETCH o.orderItems WHERE o.id = :orderId")
    Orders findOrderWithItems(Long orderId);
}
