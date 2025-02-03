package com.Soo_Shinsa.order;
import com.Soo_Shinsa.exception.NotFoundException;
import com.Soo_Shinsa.order.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import static com.Soo_Shinsa.exception.ErrorCode.NOT_FOUND_ORDER;



public interface OrdersRepository extends JpaRepository<Orders, Long> {



    Page<Orders> findAllByUserUserId(Long userid,Pageable pageable);

    default Orders findByIdOrElseThrow(Long orderId) {
        return findById(orderId).orElseThrow(
                () -> new NotFoundException(NOT_FOUND_ORDER));
    }

}
