package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.entity.Product;
import com.Soo_Shinsa.entity.ProductOption;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@RequiredArgsConstructor
public class ProductOptionResponseDto {

    private Long id;
    private String size;
    private String color;
    private String status;
    private Product productId;

    public ProductOptionResponseDto(Long id, String size, String color, String status, Product productId) {
        this.id = id;
        this.size = size;
        this.color = color;
        this.status = status;
        this.productId = productId;
    }

    public static ProductOptionResponseDto toDto(ProductOption productoption) {
        return new ProductOptionResponseDto(
                productoption.getId(),
                productoption.getSize(),
                productoption.getColor(),
                productoption.getStatus(),
                productoption.getProductId()
        );
    }
}
