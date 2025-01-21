package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.constant.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrdersUpdateRequestDto {
    @NotNull(message = "오더Id는 필수값 입니다.")
    private Long orderId;
    @NotNull(message = "상태는 필수값 입니다.")
    private Status status;
}
