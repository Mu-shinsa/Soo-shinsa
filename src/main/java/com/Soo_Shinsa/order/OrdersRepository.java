package com.Soo_Shinsa.order;

import com.Soo_Shinsa.exception.NotFoundException;
import com.Soo_Shinsa.order.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import static com.Soo_Shinsa.exception.ErrorCode.NOT_FOUND_ORDER;

public interface OrdersRepository extends JpaRepository<Orders, Long>, OrderCustomRepository {

    default Orders findByIdOrElseThrow(Long orderId) {
        return findById(orderId).orElseThrow(
                () -> new NotFoundException(NOT_FOUND_ORDER));
    }

}
