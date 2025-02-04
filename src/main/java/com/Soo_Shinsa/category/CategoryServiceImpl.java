package com.Soo_Shinsa.category;

import com.Soo_Shinsa.brand.Brand;
import com.Soo_Shinsa.brand.BrandRepository;
import com.Soo_Shinsa.brand.QBrand;
import com.Soo_Shinsa.category.dto.CategoryRequestDto;
import com.Soo_Shinsa.category.dto.CategoryResponseDto;
import com.Soo_Shinsa.category.dto.CategoryUpdateRequestDto;
import com.Soo_Shinsa.category.dto.FindCategoryResponseDto;
import com.Soo_Shinsa.category.model.Category;
import com.Soo_Shinsa.category.model.QCategory;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.utils.EntityValidator;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final JPAQueryFactory queryFactory;

    @Transactional
    @Override
    public CategoryResponseDto create(Long brandId, User user, CategoryRequestDto dto) {

        EntityValidator.validateAdminAccess(user);
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
    public CategoryResponseDto findById(Long categoryId) {

        Category findCategory = categoryRepository.findByIdOrElseThrow(categoryId);

        return CategoryResponseDto.toDto(findCategory);
    }

    @Override
    public Page<FindCategoryResponseDto> findByBrandId(Long brandId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        QCategory category = QCategory.category;
        QBrand brand = QBrand.brand;

        // Fetch Join으로 카테고리와 브랜드를 한 번에 로드
        List<Category> findCategories = queryFactory
                .selectFrom(category)
                .join(category.brand, brand).fetchJoin()
                .where(brand.id.eq(brandId)) // 브랜드 ID 조건
                .offset(pageable.getOffset()) // 페이징 시작 위치
                .limit(pageable.getPageSize()) // 페이지 크기
                .fetch();

        // 총 개수 조회 (페이징 처리를 위해 필요)
        long total = queryFactory
                .select(category.count())
                .from(category)
                .where(category.brand.id.eq(brandId))
                .fetch()
                .size();

        // Category → FindCategoryResponseDto 변환
        List<FindCategoryResponseDto> responseDtos = findCategories.stream()
                .map(FindCategoryResponseDto::of)
                .toList();

        return new PageImpl<>(responseDtos, pageable, total);
    }


    @Override
    public Page<FindCategoryResponseDto> findAll(int page, int size) {

        QCategory category = QCategory.category;
        Pageable pageable = PageRequest.of(page, size);

        List<Category> categories = queryFactory
                .selectFrom(category)
                .orderBy(category.name.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(category.count())
                .from(category)
                .fetch()
                .size();

        List<FindCategoryResponseDto> categoryResponseDtos = categories.stream()
                .map(FindCategoryResponseDto::of)
                .toList();

        return new PageImpl<>(categoryResponseDtos, pageable, total);
    }

    @Override
    public CategoryResponseDto update(User user, CategoryUpdateRequestDto dto, Long categoryId) {

        Category findCategory = categoryRepository.findByIdOrElseThrow(categoryId);

        findCategory.update(dto.getName());

        categoryRepository.save(findCategory);

        return CategoryResponseDto.toDto(findCategory);
    }
}
