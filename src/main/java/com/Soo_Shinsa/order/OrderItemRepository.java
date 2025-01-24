package com.Soo_Shinsa.order;



import com.Soo_Shinsa.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi FROM OrderItem oi " +
            "JOIN FETCH oi.order o " +
            "JOIN FETCH oi.product p " +
            "WHERE o.user.userId = :userId")
    List<OrderItem> findAllByUserIdWithFetchJoin(Long userId);


}
