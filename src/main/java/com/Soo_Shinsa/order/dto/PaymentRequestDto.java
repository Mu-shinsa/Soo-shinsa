package com.Soo_Shinsa.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PaymentRequestDto {

    private String orderId;
    private BigDecimal amount;
    private String method;
    private String customerEmail;
    private String customerName;

    public PaymentRequestDto(String orderId, BigDecimal amount, String method, String customerEmail, String customerName) {
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
    }
}