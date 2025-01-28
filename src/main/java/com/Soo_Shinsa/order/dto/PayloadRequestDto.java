package com.Soo_Shinsa.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PayloadRequestDto {
    String orderId;
    String amount;
    String cancelReason;

    public PayloadRequestDto(String orderId, String amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public PayloadRequestDto(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
