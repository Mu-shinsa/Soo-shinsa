package com.Soo_Shinsa.product.dto;

import com.Soo_Shinsa.constant.ProductStatus;
import com.Soo_Shinsa.product.model.Product;
import com.Soo_Shinsa.product.model.ProductOption;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductOptionRequestDto {

    @NotEmpty(message = "사이즈는 필수 값 입니다.")
    private String size;

    @NotEmpty(message = "색상은 필수 값 입니다.")
    private String color;

    @NotEmpty(message = "상태는 필수 값 입니다.")
    private ProductStatus status;

    public ProductOptionRequestDto(String size, String color, ProductStatus status) {
        this.size = size;
        this.color = color;
        this.status = status;
    }

    public ProductOption toEntity(Product findProduct) {
        return ProductOption.builder()
                .size(size)
                .color(color)
                .productStatus(status)
                .product(findProduct)
                .build();
    }
}