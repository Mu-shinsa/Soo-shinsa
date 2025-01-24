package com.Soo_Shinsa.brand.dto;

import com.Soo_Shinsa.brand.Brand;

public class BrandUpdateResponseDto {

    private Long id;
    private String registrationNum;
    private String name;
    private String context;
    private String status;

    public BrandUpdateResponseDto(Long id, String registrationNum, String name, String context, String status) {
        this.id = id;
        this.registrationNum = registrationNum;
        this.name = name;
        this.context = context;
        this.status = status;
    }

    public static BrandUpdateResponseDto toDto(Brand brand) {
        return new BrandUpdateResponseDto(
                brand.getId(),
                brand.getRegistrationNum(),
                brand.getName(),
                brand.getContext(),
                brand.getStatus());
    }
}
