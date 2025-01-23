package com.Soo_Shinsa.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rate;
    private String content;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @Builder
    public Review(Integer rate, String content, String imageUrl, Product product, User user, OrderItem orderItem) {
        this.rate = rate;
        this.content = content;
        this.imageUrl = imageUrl;
        this.product = product;
        this.user = user;
        this.orderItem = orderItem;
    }

    public void update(Integer rate, String content, String imageUrl) {
        if (rate != null) {
            this.rate = rate;
        }
        if (content != null) {
            this.content = content;
        }

        if (imageUrl != null) {
            this.imageUrl = imageUrl;
        }
    }
}
