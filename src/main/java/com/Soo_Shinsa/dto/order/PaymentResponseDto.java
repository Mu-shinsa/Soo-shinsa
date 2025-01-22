package com.Soo_Shinsa.dto.order;

import com.Soo_Shinsa.constant.TossPayMethod;
import com.Soo_Shinsa.constant.TossPayStatus;
import com.Soo_Shinsa.entity.Payment;
import com.Soo_Shinsa.entity.ProductOption;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PaymentResponseDto {
    private Long id;
    private String paymentKey;
    private TossPayMethod method;
    private BigDecimal amount; // 결제 및 주문 금액을 단일 필드로 통합
    private TossPayStatus status;
    private String userEmail;


    public PaymentResponseDto(Long id, String paymentKey, TossPayMethod method, BigDecimal amount, TossPayStatus status, String userEmail) {
        this.id = id;
        this.paymentKey = paymentKey;
        this.method = method;
        this.amount = amount;
        this.status = status;
        this.userEmail = userEmail;
    }

    public static PaymentResponseDto toDto(Payment payment) {
        return new PaymentResponseDto(
                payment.getId(),
                payment.getPaymentKey(),
                payment.getMethod(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getUser().getEmail()
        );
    }
}