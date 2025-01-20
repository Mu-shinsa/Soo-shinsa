package com.Soo_Shinsa.entity;

import com.Soo_Shinsa.model.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Length(min = 1, max = 10)
    private String size;

    @Column(nullable = false)
    @Length(min = 1, max = 50)
    private String color;

    @Column(nullable = false)
    @Length(min = 1, max = 15)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_Id", nullable = false)
    private Product product;

    public ProductOption(String size, String color, String status, Product product) {
        this.size = size;
        this.color = color;
        this.status = status;
        this.product = product;
    }

    public void update(String size, String color, String status) {
        this.size = size;
        this.color = color;
        this.status = status;
    }
}
