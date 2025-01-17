package com.Soo_Shinsa.constant;

public enum UserStatus {
    ACTIVE,
    DELETED,;

    public static UserStatus of(String status) {
        return UserStatus.valueOf(status.toUpperCase());
    }
}
