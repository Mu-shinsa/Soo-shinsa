package com.Soo_Shinsa.entity;

import com.Soo_Shinsa.constant.Status;
import com.Soo_Shinsa.model.BaseTimeEntity;
import com.Soo_Shinsa.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Orders extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orderNumber;

    @Column(nullable = false)
    private BigDecimal totalPrice= BigDecimal.ZERO;;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();


    public Orders(String orderNumber, BigDecimal totalPrice, Status status, User user, List<OrderItem> orderItems) {
        this.orderNumber = orderNumber;
        this.totalPrice = totalPrice;
        this.status = status;
        this.user = user;
        this.orderItems = orderItems;
    }

    // 연관관계 편의 메서드
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        calculateTotalPrice();
        // 생성자에서 이미 order 설정 완료, 별도의 set 호출 필요 없음
    }

    public void removeOrderItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
        calculateTotalPrice();
        // 삭제 시에도 연관 관계를 직접 null로 설정하지 않아도 됨

    }
    public void calculateTotalPrice() {
        this.totalPrice = orderItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
