package com.Soo_Shinsa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateDto {

    private Long id;
    private Integer rate;
    private String content;

    public ReviewUpdateDto(Long id, Integer rate, String content) {
        this.id = id;
        this.rate = rate;
        this.content = content;
    }
}
