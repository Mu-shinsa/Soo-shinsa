package com.Soo_Shinsa.dto.payment;

import com.Soo_Shinsa.model.Orders;
import com.Soo_Shinsa.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserOrderDTO {
    private User user;
    private Orders order;

    public UserOrderDTO(User user, Orders order) {
        this.user = user;
        this.order = order;
    }
}