package com.Soo_Shinsa.brand.dto;

import com.Soo_Shinsa.constant.BrandStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BrandRequestDto {

    private String registrationNum;

    private String name;

    private String context;

    private BrandStatus status;

}
