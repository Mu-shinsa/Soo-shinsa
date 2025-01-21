package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.constant.TossPayMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PaymentRequestDto {
    @NotEmpty(message = "키값을 입력해주세요.")
    private String paymentKey; // 토스페이먼츠 결제 키
    @NotEmpty(message = "결제할 방법을 입력해주세요.")
    private TossPayMethod method; // 결제 수단 (CARD)
    @NotNull(message = "오더Id는 필수값 입니다.")
    private Long orderId; // 주문 ID
    @NotNull(message = "유저Id는 필수값 입니다.")
    private Long userId; // 사용자 ID

    public PaymentRequestDto(String paymentKey, TossPayMethod method, Long orderId, Long userId) {
        this.paymentKey = paymentKey;
        this.method = method;
        this.orderId = orderId;
        this.userId = userId;
    }
}