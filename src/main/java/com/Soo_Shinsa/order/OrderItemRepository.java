package com.Soo_Shinsa.order;


import com.Soo_Shinsa.exception.NotFoundException;
import com.Soo_Shinsa.order.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import static com.Soo_Shinsa.exception.ErrorCode.NOT_FOUND_ORDER_OPTION;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = "SELECT oi FROM OrderItem oi " +
            "JOIN FETCH oi.order o " +
            "JOIN FETCH oi.product p " +
            "WHERE o.user.userId = :userId",
            countQuery = "SELECT COUNT(oi) FROM OrderItem oi " +
                    "JOIN oi.order o " +
                    "WHERE o.user.userId = :userId")
    Page<OrderItem> findAllByUserIdWithFetchJoin(Long userId, Pageable pageable);

    default OrderItem findByIdOrElseThrow(Long orderItemId) {
        return findById(orderItemId).orElseThrow(
                () -> new NotFoundException(NOT_FOUND_ORDER_OPTION));
    }
}
