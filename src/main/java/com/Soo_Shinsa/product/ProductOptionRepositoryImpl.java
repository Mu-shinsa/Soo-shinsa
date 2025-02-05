package com.Soo_Shinsa.product;

import com.Soo_Shinsa.exception.ErrorCode;
import com.Soo_Shinsa.exception.InvalidInputException;
import com.Soo_Shinsa.product.dto.FindProductOptionRequestDto;
import com.Soo_Shinsa.product.dto.ProductOptionResponseDto;
import com.Soo_Shinsa.product.model.QProductOption;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ProductOptionRepositoryImpl implements ProductOptionCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProductOptionResponseDto> findProductsByOptionalSizeAndColor(FindProductOptionRequestDto requestDto, Pageable pageable) {
        QProductOption productOption = QProductOption.productOption;

        if (requestDto.getColor() == null && requestDto.getSize() == null) {
            throw new InvalidInputException(ErrorCode.SELECT_COLOR_OR_SIZE);
        }

        BooleanBuilder builder = new BooleanBuilder();

        if (requestDto.getColor() != null) {
            builder.and(productOption.color.eq(requestDto.getColor()));
        }

        if (requestDto.getSize() != null) {
            builder.and(productOption.size.eq(requestDto.getSize()));
        }

        return new PageImpl<>(
                queryFactory
                        .select(Projections.constructor(ProductOptionResponseDto.class,
                                productOption.id,
                                productOption.size,
                                productOption.color,
                                productOption.product.id,
                                Expressions.numberTemplate(Long.class, "COUNT(*) OVER()")
                        ))
                        .from(productOption)
                        .where(builder)
                        .orderBy(productOption.id.asc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch(),
                pageable,
                queryFactory
                        .select(productOption.count())
                        .from(productOption)
                        .where(builder)
                        .fetch()
                        .size()
        );
    }
}
