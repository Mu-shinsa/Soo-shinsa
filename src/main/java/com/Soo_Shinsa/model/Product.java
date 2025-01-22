package com.Soo_Shinsa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Length(min = 1, max = 255)
    private String name;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    @Length(min = 1, max = 15)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_Id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catogory_id", nullable = false)
    private Category category;

    public Product(String name, BigDecimal price, String status, Brand brand, Category category) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.brand = brand;
        this.category = category;
    }

    public void update(String name, BigDecimal price, String status) {
        this.name = name;
        this.price = price;
        this.status = status;
    }
}
