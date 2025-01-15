package com.Soo_Shinsa.repository;

import com.Soo_Shinsa.entity.OrderItem;
import com.Soo_Shinsa.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
