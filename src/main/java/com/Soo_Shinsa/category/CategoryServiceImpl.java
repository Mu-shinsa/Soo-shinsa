package com.Soo_Shinsa.category;

import com.Soo_Shinsa.brand.Brand;
import com.Soo_Shinsa.brand.BrandRepository;
import com.Soo_Shinsa.category.dto.CategoryRequestDto;
import com.Soo_Shinsa.category.dto.CategoryResponseDto;
import com.Soo_Shinsa.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.Soo_Shinsa.category.Category.rootParent;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Transactional
    @Override
    public CategoryResponseDto create(Long brandId, User user, CategoryRequestDto dto) {

        Brand findBrand = brandRepository.findByIdOrElseThrow(brandId);

 product/option/brand/address
        Category parent = null;
        if (dto.getParent() != null) {
            parent = categoryRepository.findByIdOrElseThrow(dto.getParent());
        }

        Category saveCategory = Category.builder()
                .brand(findBrand)
                .parent(parent)
                .name(dto.getName())
                .build();

        categoryRepository.save(saveCategory);
      
        return CategoryResponseDto.toDto(saveCategory);
    }
}
