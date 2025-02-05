package com.Soo_Shinsa.brand;

import com.Soo_Shinsa.brand.dto.FindBrandAllResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class BrandRepositoryImpl implements BrandCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FindBrandAllResponseDto> getAllBrand(Pageable pageable) {
        QBrand brand = QBrand.brand;

        return new PageImpl<>(
                queryFactory
                        .select(Projections.constructor(FindBrandAllResponseDto.class,
                                brand.id,
                                brand.name,
                                Expressions.numberTemplate(Long.class, "COUNT(*) OVER()")
                        ))
                        .from(brand)
                        .orderBy(brand.name.asc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch(),
                pageable,
                queryFactory
                        .select(brand.count())
                        .from(brand)
                        .fetch()
                        .size()
        );
    }
}
