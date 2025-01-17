package com.Soo_Shinsa.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
public class BrandRequestDto {

    private String registrationNum;

    private String name;

    private String context;

    private String status;
}
