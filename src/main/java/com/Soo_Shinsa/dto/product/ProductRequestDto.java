package com.Soo_Shinsa.dto.product;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class ProductRequestDto {

    private String name;

    private BigDecimal price;

    private String status;

}
