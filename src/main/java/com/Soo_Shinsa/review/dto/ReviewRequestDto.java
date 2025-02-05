package com.Soo_Shinsa.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {


    @NotNull(message = "별점은 필수 입니다.")
    @Min(value = 1, message = "1점 이상이어야 합니다")
    @Max(value = 5, message = "5점 이하여야 합니다")
    private Integer rate;       // 평점

    @NotEmpty(message = "리뷰 내용을 입력해주세요.")
    private String content;     // 리뷰 내용

    @NotNull(message = "상품 ID를 입력해주세요.")
    private Long productId;     // 상품 ID

    private String imageUrl;    // 이미지 URL


    public ReviewRequestDto(Integer rate, String content, Long productId, String imageUrl) {
        this.rate = rate;
        this.content = content;
        this.productId = productId;
        this.imageUrl = imageUrl;
    }
}
