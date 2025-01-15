package com.Soo_Shinsa.entity;

import com.Soo_Shinsa.constant.UserStatus;
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
@Table(name = "cartitems")
public class CartItem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productoption_Id", nullable = false)
    private ProductOption productOption;


    public CartItem(int quantity, User user, ProductOption productOption) {
        this.quantity = quantity;
        this.user = user;
        this.productOption = productOption;
    }


    public void updateCartItem(Integer quantity) {
        this.quantity = quantity;
    }
}
