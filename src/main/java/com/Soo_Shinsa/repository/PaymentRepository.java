package com.Soo_Shinsa.repository;

import com.Soo_Shinsa.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
