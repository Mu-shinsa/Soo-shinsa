package com.Soo_Shinsa.repository;

import com.Soo_Shinsa.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByPaymentKey(String paymentKey);

    Payment findByOrderId(String orderId);
}
