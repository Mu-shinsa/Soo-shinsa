package com.Soo_Shinsa.constant;

import lombok.Getter;

@Getter
public enum OrdersStatus {
    BEFOREPAYMENT("결제 전"),
    PAYMENTCOMPLETED("결제 완료"),
    ONDELIVERY("배송 시작"),
    DELIVERYCOMPLETED("배송 완료");

    private final String message;

    OrdersStatus(String message) {
        this.message = message;
    }
}
