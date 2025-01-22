package com.Soo_Shinsa.dto.review;

import com.Soo_Shinsa.model.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateDto {

    private Long id;
    private Integer rate;
    private String content;

    @Builder
    public ReviewUpdateDto(Long id, Integer rate, String content) {
        this.id = id;
        this.rate = rate;
        this.content = content;
    }

    public static ReviewUpdateDto toDto(Review saveReview) {
        return ReviewUpdateDto.builder()
                .rate(saveReview.getRate())
                .content(saveReview.getContent())
                .build();
    }
}
