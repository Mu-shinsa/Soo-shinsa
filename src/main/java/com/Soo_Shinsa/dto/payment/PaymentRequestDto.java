package com.Soo_Shinsa.dto.payment;

import com.Soo_Shinsa.constant.TossPayMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Getter
@NoArgsConstructor
public class PaymentRequestDto {
    private String paymentKey; // 토스페이먼츠 결제 키
    @NotNull(message = "결제방법을 입력해주세요.")
    private TossPayMethod method; // 결제 수단 (CARD)
    @NotNull(message = "오더Id는 필수값 입니다.")
    private String orderId; // 주문 ID
    @NotNull(message = "오더는 필수값 입니다.")
    private Long order; // 주문 ID





    public PaymentRequestDto(String paymentKey, TossPayMethod method, String orderId,Long order) {
        this.paymentKey = paymentKey;
        this.method = method;
        this.orderId = orderId;
        this.order=order;

    }
}