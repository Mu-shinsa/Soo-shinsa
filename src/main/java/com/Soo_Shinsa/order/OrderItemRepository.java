package com.Soo_Shinsa.order;



import com.Soo_Shinsa.order.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi FROM OrderItem oi " +
            "JOIN FETCH oi.order o " +
            "JOIN FETCH oi.product p " +
            "WHERE o.user.userId = :userId")
    List<OrderItem> findAllByUserIdWithFetchJoin(Long userId);



    @Query(value = "SELECT oi FROM OrderItem oi " +
            "JOIN FETCH oi.order o " +
            "JOIN FETCH oi.product p " +
            "WHERE o.user.userId = :userId",
            countQuery = "SELECT COUNT(oi) FROM OrderItem oi " +
                    "JOIN oi.order o " +
                    "WHERE o.user.userId = :userId")
    Page<OrderItem> findAllByUserIdWithFetchJoin(Long userId, Pageable pageable);
}
