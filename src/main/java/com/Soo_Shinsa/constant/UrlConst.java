package com.Soo_Shinsa.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class UrlConst {
    //로그인 필터 화이트 리스트
    public static final String[] WHITE_LIST = {"/", "/auth/signin", "/auth/login"};

    //사장 인터셉터 리스트
    public static final String[] ADMIN_INTERCEPTOR_LIST = {"/admin", "/admin/**"};

    //사장 인터셉터 리스트
    public static final String[] VENDOR_INTERCEPTOR_LIST = {"/owner", "/owner/**"};

    //손님 인터셉터 리스트
    public static final String[] CUSTOMER_INTERCEPTOR_LIST = {"/users", "/users/**"};

}
