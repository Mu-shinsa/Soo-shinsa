package com.Soo_Shinsa.entity;

import com.Soo_Shinsa.constant.Status;
import com.Soo_Shinsa.model.BaseTimeEntity;
import com.Soo_Shinsa.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Orders extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private String orderNumber;

    @Column(nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;




    public Orders(String orderNumber, Double totalPrice, Status status, User user) {
        this.orderNumber = orderNumber;
        this.totalPrice = totalPrice;
        this.status = status;
        this.user = user;
    }
}
