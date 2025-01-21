package com.Soo_Shinsa.constant;

import lombok.Getter;

@Getter
public enum TossPayMethod {
    Card("카드");
    private String message;

    TossPayMethod(String message) {
        this.message = this.name();
    }

}
