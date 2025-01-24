package com.Soo_Shinsa.product.model;

import com.Soo_Shinsa.constant.ProductStatus;
import com.Soo_Shinsa.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String size;

    private String color;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_Id", nullable = false)
    private Product product;

    @Builder
    public ProductOption(String size, String color, ProductStatus productStatus, Product product) {
        this.size = size;
        this.color = color;
        this.productStatus = productStatus;
        this.product = product;
    }

    public void update(String size, String color, ProductStatus productStatus) {

        if (size != null) {
            this.size = size;
        }

        if (color != null) {
            this.color = color;
        }

        if (productStatus != null) {
            this.productStatus = productStatus;
        }
    }
}
