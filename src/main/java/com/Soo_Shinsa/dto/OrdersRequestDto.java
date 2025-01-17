package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.constant.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrdersRequestDto {
    @NotBlank(message = "주문번호는 필수값 입니다..")
    private String orderNumber;
}
