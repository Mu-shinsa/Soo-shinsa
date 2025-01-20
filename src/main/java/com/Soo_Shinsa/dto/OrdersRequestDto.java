package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrdersRequestDto {


    private Long userId;

    public OrdersRequestDto(Long userId) {
        this.userId = userId;
    }
}