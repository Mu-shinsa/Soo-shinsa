package com.Soo_Shinsa.category.dto;

import com.Soo_Shinsa.brand.Brand;
import com.Soo_Shinsa.category.Category;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Getter
public class CategoryRequestDto {

    @NotNull(message = "브랜드 ID를 입력해주세요.")
    private Long brand;

    private Long parent;

    @NotNull(message = "카테고리 이름을 입력해주세요.")
    private String name;

    public Category saveParent(Brand brand, Category savedParent) {
        Category parent = !Objects.equals(savedParent,
                Category.rootParent()) ? savedParent : null;

        return new Category(brand, parent, name);
    }

    public Boolean hasParent() {
        return parent != null;
    }
}
