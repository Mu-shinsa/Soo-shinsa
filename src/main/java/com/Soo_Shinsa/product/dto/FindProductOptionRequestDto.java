package com.Soo_Shinsa.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindProductOptionRequestDto {

    private String size;
    private String color;


    public FindProductOptionRequestDto(String size, String color) {
        this.size = size;
        this.color = color;
    }
}
