package com.Soo_Shinsa.review.dto;

import com.Soo_Shinsa.review.model.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {

    private Long id;
    private Long userId;
    private Long productId;
    private String imageUrl;
    private Long orderItemId;
    private Integer rate;
    private String content;

    @Builder
    public ReviewResponseDto(Long id, Long userId, Long productId, String imageUrl, Long orderItemId, Integer rate, String content) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.orderItemId = orderItemId;
        this.rate = rate;
        this.content = content;
    }


    public static ReviewResponseDto toDto(Review review) {
        return ReviewResponseDto.builder()
                .id(review.getId())
                .productId(review.getProduct().getId())
                .userId(review.getUser().getUserId())
                .orderItemId(review.getOrderItem().getId())
                .rate(review.getRate())
                .content(review.getContent())
                .imageUrl(review.getImageUrl())
                .build();
    }
}
