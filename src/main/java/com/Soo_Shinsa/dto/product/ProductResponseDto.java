package com.Soo_Shinsa.dto.product;

import com.Soo_Shinsa.model.Brand;
import com.Soo_Shinsa.model.Product;
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
    private Brand brand;

    public ProductResponseDto(Long id, String name, BigDecimal price, String status, Brand brand) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.brand = brand;
    }

    public static ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStatus(),
                product.getBrand()
                );
    }
}
