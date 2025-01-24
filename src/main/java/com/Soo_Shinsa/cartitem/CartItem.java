package com.Soo_Shinsa.cartitem;

import com.Soo_Shinsa.BaseTimeEntity;
import com.Soo_Shinsa.product.model.ProductOption;
import com.Soo_Shinsa.utils.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "cartitems")
public class CartItem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
