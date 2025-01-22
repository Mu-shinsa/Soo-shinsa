package com.Soo_Shinsa.dto.payment;

import com.Soo_Shinsa.constant.TossPayMethod;
import com.Soo_Shinsa.constant.TossPayStatus;
import com.Soo_Shinsa.model.Payment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PaymentResponseDto {
    private Long id;
    private TossPayMethod method;
    private BigDecimal amount; // 결제 및 주문 금액을 단일 필드로 통합
    private TossPayStatus status;
    private String userEmail;
    private String orderId;


    public PaymentResponseDto(Long id, TossPayMethod method, BigDecimal amount, TossPayStatus status, String userEmail,String orderId) {
        this.id = id;
        this.method = method;
        this.amount = amount;
        this.status = status;
        this.userEmail = userEmail;
        this.orderId=orderId;
    }

    public static PaymentResponseDto toDto(Payment payment) {
        return new PaymentResponseDto(
                payment.getId(),
                payment.getMethod(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getUser().getEmail(),
                payment.getOrderId()
        );
    }
}