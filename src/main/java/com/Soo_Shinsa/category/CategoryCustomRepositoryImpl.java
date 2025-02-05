package com.Soo_Shinsa.category;

import com.Soo_Shinsa.brand.QBrand;
import com.Soo_Shinsa.category.dto.FindCategoryResponseDto;
import com.Soo_Shinsa.category.model.QCategory;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class CategoryCustomRepositoryImpl implements CategoryCustomRepository {
    private final JPAQueryFactory queryFactory;


    @Override
    public Page<FindCategoryResponseDto> findAllCategories(Pageable pageable) {

        QCategory category = QCategory.category;


        return new PageImpl<>(
                queryFactory
                        .select(Projections.constructor(FindCategoryResponseDto.class,
                                category.id,
                                category.name,
                                category.parent.id,
                                Expressions.numberTemplate(Long.class, "COUNT(*) OVER()")
                        ))
                        .from(category)
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch(),
                pageable,
                queryFactory
                        .select(category.count())
                        .from(category)
                        .fetch()
                        .size());
    }

    @Override
    public Page<FindCategoryResponseDto> findByBrandId(Long brandId, Pageable pageable) {
        QCategory category = QCategory.category;
        QBrand brand = QBrand.brand;

        return new PageImpl<>(
                queryFactory
                        .select(Projections.constructor(FindCategoryResponseDto.class,
                                category.id,
                                category.name,
                                category.parent.id,
                                Expressions.numberTemplate(Long.class, "COUNT(*) OVER()")
                        ))
                        .from(category)
                        .join(category.brand, brand)
                        .where(brand.id.eq(brandId))
                        .orderBy(category.name.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch(),
                pageable,
                queryFactory
                        .select(category.count())
                        .from(category)
                        .where(brand.id.eq(brandId))
                        .fetch()
                        .size()
        );
    }
}
