package com.Soo_Shinsa.dto.category;

import com.Soo_Shinsa.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryRequestDto {

    private Category parent;

    private String name;
}
