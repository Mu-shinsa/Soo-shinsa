package com.Soo_Shinsa.category;

import com.Soo_Shinsa.brand.Brand;
import com.Soo_Shinsa.brand.BrandRepository;
import com.Soo_Shinsa.category.dto.CategoryRequestDto;
import com.Soo_Shinsa.category.dto.CategoryResponseDto;
import com.Soo_Shinsa.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Transactional
    @Override
    public CategoryResponseDto create(User user, CategoryRequestDto dto, Long brandId) {

        Brand findBrand = brandRepository.findByIdOrElseThrow(brandId);
        Long dtoParentId = dto.getParent();

        if(dtoParentId != null){
            Category parent = categoryRepository.findByIdOrElseThrow(dto.getParent());
            Category savedCategory = new Category(
                    findBrand,
                    parent,
                    dto.getName()
            );
            return CategoryResponseDto.toDto(savedCategory);
        }
        Category savedCategory = new Category(
                findBrand,
                null,
                dto.getName()
        );
        return CategoryResponseDto.toDto(savedCategory);
    }
}
