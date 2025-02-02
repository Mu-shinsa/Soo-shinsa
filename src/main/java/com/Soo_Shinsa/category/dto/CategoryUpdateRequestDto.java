package com.Soo_Shinsa.category.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryUpdateRequestDto {

    @NotNull(message = "카테고리 이름을 입력해주세요.")
    private String name;

}
