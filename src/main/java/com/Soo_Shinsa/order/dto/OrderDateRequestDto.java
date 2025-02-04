package com.Soo_Shinsa.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class OrderDateRequestDto {
    private LocalDate startDate;
    private LocalDate endDate;

    public OrderDateRequestDto(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
