package com.Soo_Shinsa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CartItemRequestDto {

    @NotNull(message = "상품옵션은 필수값 입니다.")
    private Long optionId;
    @NotNull(message = "수량 필수값 입니다.")
    private Integer quantity;
}
