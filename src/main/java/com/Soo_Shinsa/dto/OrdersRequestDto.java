package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.constant.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OrdersRequestDto {
    @NotBlank(message = "상품옵션은 필수값 입니다.")
    private Status status;
    @NotBlank(message = "수량 필수값 입니다.")
    private int quantity;
}
