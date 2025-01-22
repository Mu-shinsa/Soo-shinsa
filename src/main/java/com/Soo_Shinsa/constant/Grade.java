package com.Soo_Shinsa.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Grade {
    ROOKIE("ROOKIE"),
    BRONZE("BRONZE"),
    SILVER("SILVER"),
    GOLD("GOLD");

    private final String name;
}
