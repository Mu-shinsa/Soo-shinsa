package com.Soo_Shinsa.order.dto;

import jakarta.validation.constraints.NotNull;

public class PaymentCancelRequestDto {
    @NotNull(message = "토스페이먼츠 결제 키를 입력해주세요.")
    private String paymentKey; // 토스페이먼츠 결제 키

}
