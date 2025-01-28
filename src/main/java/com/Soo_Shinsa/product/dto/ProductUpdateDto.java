package com.Soo_Shinsa.product.dto;

import com.Soo_Shinsa.brand.Brand;
import com.Soo_Shinsa.constant.ProductStatus;
import com.Soo_Shinsa.product.model.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ProductUpdateDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private ProductStatus status;
    private Brand brand;
    private String imageUrl;

    @Builder
    public ProductUpdateDto(Long id, String name, BigDecimal price, ProductStatus status, Brand brand, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.brand = brand;
        this.imageUrl = imageUrl;
    }

    public static ProductUpdateDto toDto(Product product) {
        return ProductUpdateDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .status(product.getProductStatus())
                .brand(product.getBrand())
                .imageUrl(product.getImageUrl())
                .build();
    }
}
