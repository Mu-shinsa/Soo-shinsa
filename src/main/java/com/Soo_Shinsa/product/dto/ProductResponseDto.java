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
    private ProductStatus status;
    private Long brandId;
    private Long categoryId;
    private String imageUrl;

    @Builder
    public ProductResponseDto(Long id, String name, BigDecimal price, ProductStatus status, Long brandId, Long categoryId, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
    }

    public static ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getProductStatus(),
                product.getBrand().getId(),
                product.getCategory().getId(),
                product.getImageUrl()
        );
    }
}
