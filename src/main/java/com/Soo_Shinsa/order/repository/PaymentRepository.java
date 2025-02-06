package com.Soo_Shinsa.order.repository;

import com.Soo_Shinsa.order.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByPaymentKey(String paymentKey);

    Payment findByOrderId(String orderId);
}
