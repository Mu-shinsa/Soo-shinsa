package com.Soo_Shinsa.utils;

import java.util.Base64;

public class AuthUtils {
    public static String createAuthHeader(String secretKey) {
        String credentials = secretKey + ":";
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}