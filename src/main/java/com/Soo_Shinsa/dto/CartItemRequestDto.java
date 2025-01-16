package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.entity.ProductOption;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CartItemRequestDto {

    @NotBlank(message = "상품옵션은 필수값 입니다.")
    private Long optionId;
    @NotBlank(message = "수량 필수값 입니다.")
    private int quantity;
}
