package com.Soo_Shinsa.entity;

import com.Soo_Shinsa.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orderItems")
public class OrderItem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", nullable = false)
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public OrderItem(Integer quantity, Orders order, Product product) {
        this.quantity = quantity;
        this.order = order;
        this.product = product;
    }

    public void updateOrderItem(Integer quantity) {
        this.quantity = quantity;
    }


}
