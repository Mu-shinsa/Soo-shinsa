package com.Soo_Shinsa.repository;



import com.Soo_Shinsa.entity.Orders;
import com.Soo_Shinsa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface OrdersRepository extends JpaRepository<Orders, Long> {



    Page<Orders> findAllByUserUserId(Long userid, Pageable pageable);

    Orders findByUserUserId(Long userId);

}
