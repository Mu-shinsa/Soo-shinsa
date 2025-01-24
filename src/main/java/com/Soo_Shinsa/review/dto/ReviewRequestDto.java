package com.Soo_Shinsa.review.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {

    @NotNull(message = "평점을 입력해주세요.")
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
