package com.Soo_Shinsa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentResponseDto {

    private String paymentKey;
    private String orderId;
    private String status;
    private String method;
    private String receiptUrl;

    public PaymentResponseDto(String paymentKey, String orderId, String status, String method, String receiptUrl) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.status = status;
        this.method = method;
        this.receiptUrl = receiptUrl;
    }
}
