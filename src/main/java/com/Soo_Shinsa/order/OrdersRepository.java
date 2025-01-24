package com.Soo_Shinsa.order;


import com.Soo_Shinsa.order.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrdersRepository extends JpaRepository<Orders, Long> {



    Page<Orders> findAllByUserUserId(Long userid, Pageable pageable);

    Orders findByUserUserId(Long userId);

}
