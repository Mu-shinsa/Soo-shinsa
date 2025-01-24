package com.Soo_Shinsa.dto.product;


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

    @NotEmpty(message = "이미지 URL은 필수 값 입니다.")
    private String imageUrl;

    private ProductStatus status;

    @Builder
    public ProductRequestDto(String name, BigDecimal price, String imageUrl, ProductStatus status) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.status = status;
    }
}

