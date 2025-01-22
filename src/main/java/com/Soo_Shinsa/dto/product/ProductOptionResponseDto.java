package com.Soo_Shinsa.dto.product;

import com.Soo_Shinsa.model.Product;
import com.Soo_Shinsa.model.ProductOption;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductOptionResponseDto {

    private Long id;
    private String size;
    private String color;
    private String status;
    private Product product;

    public ProductOptionResponseDto(Long id, String size, String color, String status, Product product) {
        this.id = id;
        this.size = size;
        this.color = color;
        this.status = status;
        this.product = product;
    }

    public static ProductOptionResponseDto toDto(ProductOption productoption) {
        return new ProductOptionResponseDto(
                productoption.getId(),
                productoption.getSize(),
                productoption.getColor(),
                productoption.getStatus(),
                productoption.getProduct()
        );
    }
}
