package com.Soo_Shinsa.repository;


import com.Soo_Shinsa.entity.CartItem;
import com.Soo_Shinsa.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface OrdersRepository extends JpaRepository<Orders, Long> {



    Page<Orders> findAllByUserUserId(Long userid, Pageable pageable);

    Orders findByUserUserId(Long userId);

}
