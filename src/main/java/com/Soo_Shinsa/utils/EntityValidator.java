package com.Soo_Shinsa.utils;

import com.Soo_Shinsa.user.model.User;

public class EntityValidator {

    public static void validateUserOwnership(Long userId, Long ownerId, String message) {
        if (!userId.equals(ownerId)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void validateAdminAccess(User user) {
        if (!user.isAdmin()) {
            throw new IllegalArgumentException("관리자만 접근 가능합니다.");
        }
    }

    public static void validateAdminOrVendorAccess(User user) {
        if (!user.isAdminOrVendor()) {
            throw new IllegalArgumentException("관리자 또는 판매자만 접근 가능합니다.");
        }
    }
}
