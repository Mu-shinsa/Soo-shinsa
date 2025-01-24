package com.Soo_Shinsa.dto.product;

import com.Soo_Shinsa.constant.ProductStatus;
import com.Soo_Shinsa.model.Product;
import com.Soo_Shinsa.model.ProductOption;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductOptionResponseDto {

    private Long id;
    private String size;
    private String color;
    private ProductStatus status;
    private Product product;

    @Builder
    public ProductOptionResponseDto(Long id, String size, String color, ProductStatus status, Product product) {
        this.id = id;
        this.size = size;
        this.color = color;
        this.status = status;
        this.product = product;
    }

    public static ProductOptionResponseDto toDto(ProductOption savedOption) {
        return ProductOptionResponseDto.builder()
                .color(savedOption.getColor())
                .product(savedOption.getProduct())
                .status(ProductStatus.AVAILABLE)
                .size(savedOption.getSize())
                .build();
    }
}
