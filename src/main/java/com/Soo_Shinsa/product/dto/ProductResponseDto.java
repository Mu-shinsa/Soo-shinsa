package com.Soo_Shinsa.product.dto;

import com.Soo_Shinsa.constant.ProductStatus;
import com.Soo_Shinsa.brand.Brand;
import com.Soo_Shinsa.product.model.Product;
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
    private Brand brand;
    private String imageUrl;

    public ProductResponseDto(Long id, String name, BigDecimal price, ProductStatus status, Brand brand, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.brand = brand;
        this.imageUrl = imageUrl;
    }

    public static ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getProductStatus(),
                product.getBrand(),
                product.getImageUrl()
        );
    }
}
