package com.Soo_Shinsa.constant;

import lombok.Getter;

@Getter
public enum ProductStatus {
    AVAILABLE("구매 가능"),
    SOLD_OUT("매진"),
    DISCONTINUED ("할인중");

    private final String message;

    ProductStatus(String message) {
        this.message = message;
    }
}

