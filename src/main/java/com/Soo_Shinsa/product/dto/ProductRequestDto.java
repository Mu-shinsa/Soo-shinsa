package com.Soo_Shinsa.product.dto;


import com.Soo_Shinsa.constant.ProductStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class ProductRequestDto {

    @NotEmpty(message = "제품 이름은 필수 값 입니다.")
    private String name;
    @NotNull(message = "가격은 필수 입니다.")
    private BigDecimal price;

    @NotNull(message = "카테고리 아이디는 필수 값 입니다.")
    private Long categoryId;

    private ProductStatus status;

    @Builder
    public ProductRequestDto(String name, BigDecimal price, Long categoryId, ProductStatus status) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.status = status;
    }
}

