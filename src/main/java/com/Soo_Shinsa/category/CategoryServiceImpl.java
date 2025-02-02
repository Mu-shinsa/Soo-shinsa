package com.Soo_Shinsa.category;

import com.Soo_Shinsa.brand.Brand;
import com.Soo_Shinsa.brand.BrandRepository;
import com.Soo_Shinsa.category.dto.CategoryRequestDto;
import com.Soo_Shinsa.category.dto.CategoryResponseDto;
import com.Soo_Shinsa.category.dto.CategoryUpdateRequestDto;
import com.Soo_Shinsa.category.dto.FindCategoryResponseDto;
import com.Soo_Shinsa.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Transactional
    @Override
    public CategoryResponseDto create(Long brandId, User user, CategoryRequestDto dto) {

        Brand findBrand = brandRepository.findByIdOrElseThrow(brandId);

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

    @Override
    public CategoryResponseDto findById(Long categoryId, User user) {

        Category findCategory = categoryRepository.findByIdOrElseThrow(categoryId);

        return CategoryResponseDto.toDto(findCategory);
    }

    @Override
    public List<FindCategoryResponseDto> findByBrandId(Long brandId, User user) {

        List<Category> findCategories = categoryRepository.findByBrandId(brandId);

        return findCategories.stream()
                .map(FindCategoryResponseDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public Page<FindCategoryResponseDto> findAll(int page, int size, User user) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Category> categories = categoryRepository.findAllCategory(pageable);

        return categories.map(FindCategoryResponseDto::of);
    }

    @Override
    public CategoryResponseDto update(User user, CategoryUpdateRequestDto dto, Long categoryId) {

        Category findCategory = categoryRepository.findByIdOrElseThrow(categoryId);

        findCategory.update(dto.getName());

        return CategoryResponseDto.toDto(findCategory);
    }
}
