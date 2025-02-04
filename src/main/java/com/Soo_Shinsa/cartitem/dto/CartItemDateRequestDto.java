package com.Soo_Shinsa.cartitem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CartItemDateRequestDto {
    private LocalDate startDate;
    private LocalDate endDate;

    public CartItemDateRequestDto(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
