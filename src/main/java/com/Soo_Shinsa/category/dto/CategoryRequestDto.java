package com.Soo_Shinsa.category.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryRequestDto {

//    @NotNull(message = "브랜드 ID를 입력해주세요.")
//    private Long brand;

    private Long parent;

    @NotNull(message = "카테고리 이름을 입력해주세요.")
    private String name;

}
