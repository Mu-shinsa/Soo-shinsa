package com.Soo_Shinsa.order.model;

import com.Soo_Shinsa.constant.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "payments")
public class Payment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String paymentKey; // 토스페이먼츠에서 제공하는 결제 고유 키

    @Column(nullable = false)
    private String orderId; // Orders의 orderNumber와 매핑되는 값

    @Column(nullable = false)
    private BigDecimal amount; // 결제 금액

    @Column(nullable = false)
    private String status; // 결제 상태 (예: READY, IN_PROGRESS, DONE, CANCELLED)

    @Column(nullable = false)
    private String method; // 결제 수단 (예: CARD, BANK_TRANSFER)

    @Column(nullable = true)
    private String receiptUrl; // 결제 완료 후 영수증 URL

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", nullable = false)
    private Orders order;

    public Payment(String paymentKey, String orderId, BigDecimal amount, String status, String method, String receiptUrl, Orders order) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
        this.method = method;
        this.receiptUrl = receiptUrl;
        this.order = order;
    }
}