package com.Soo_Shinsa.category.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryRequestDto {

    private Long parent;

    @NotNull(message = "카테고리 이름을 입력해주세요.")
    private String name;

}
