package com.Soo_Shinsa.repository;


import com.Soo_Shinsa.entity.CartItem;
import com.Soo_Shinsa.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface OrdersRepository extends JpaRepository<Orders, Long> {


    @Query("SELECT o FROM Orders o JOIN FETCH o.orderItems WHERE o.id = :orderId AND o.user.userId = :userId")
    Orders findOrderWithItemsByUserIdAndOrderId(Long orderId, Long userId);

    Page<Orders> findAllByUserUserId(Long userid, Pageable pageable);

}
