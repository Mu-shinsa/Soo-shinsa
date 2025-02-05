package com.Soo_Shinsa.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRateDto {

    @NotNull(message = "상품 ID를 입력해주세요.")
    private Long productId;

    @Min(value = 1, message = "1점 이상이어야 합니다")
    private Integer maxRate;

    @Max(value = 5, message = "5점 이하여야 합니다")
    private Integer minRate;

    public ReviewRateDto(Long productId, Integer maxRate, Integer minRate) {
        this.productId = productId;
        this.maxRate = maxRate;
        this.minRate = minRate;
    }
}
