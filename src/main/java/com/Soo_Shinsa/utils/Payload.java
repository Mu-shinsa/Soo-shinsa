package com.Soo_Shinsa.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Payload {
    String orderId;
    Long amount;
    String cancelReason;

    public Payload(String orderId, Long amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public Payload(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
