package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.entity.Brand;
import com.Soo_Shinsa.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class ProductResponseDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private String status;
    private Brand brandId;

    public ProductResponseDto(Long id, String name, BigDecimal price, String status, Brand brandId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.brandId = brandId;
    }

    public static ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStatus(),
                product.getBrandId()
                );
    }
}
