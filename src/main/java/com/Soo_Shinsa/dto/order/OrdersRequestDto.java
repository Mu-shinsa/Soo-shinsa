package com.Soo_Shinsa.dto.order;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
public class OrdersRequestDto {

    @NotNull(message = "유저Id는 필수값 입니다.")
    private Long userId;

    public OrdersRequestDto(Long userId) {
        this.userId = userId;
    }
}