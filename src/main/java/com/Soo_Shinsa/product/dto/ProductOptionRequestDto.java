package com.Soo_Shinsa.product.dto;

import com.Soo_Shinsa.constant.ProductStatus;
import com.Soo_Shinsa.product.model.Product;
import com.Soo_Shinsa.product.model.ProductOption;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductOptionRequestDto {

    private String size;

    private String color;

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