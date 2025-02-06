package com.Soo_Shinsa.product.dto;

import com.Soo_Shinsa.constant.ProductStatus;
import com.Soo_Shinsa.product.model.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class ProductResponseDto {


    private Long id;
    private String name;
    private BigDecimal price;
    private String imageUrl; // 추가된 필드
    private ProductStatus status;
    private Long brandId;
    private Long categoryId;
    private Long count; // 추가된 필드

    @Builder
    public ProductResponseDto(Long id, String name, BigDecimal price, String imageUrl, ProductStatus status, Long brandId, Long categoryId, Long count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl; // 추가된 필드 초기화
        this.status = status;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.count = count; // 추가된 필드 초기화
    }


    public static ProductResponseDto toDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .status(product.getProductStatus())
                .brandId(product.getBrand().getId())
                .categoryId(product.getCategory().getId())
                .build();
    }
}
