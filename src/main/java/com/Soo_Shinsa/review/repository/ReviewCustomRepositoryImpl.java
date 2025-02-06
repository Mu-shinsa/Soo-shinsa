package com.Soo_Shinsa.review.repository;

import com.Soo_Shinsa.product.model.QProduct;
import com.Soo_Shinsa.review.dto.ReviewRateDto;
import com.Soo_Shinsa.review.dto.ReviewResponseDto;
import com.Soo_Shinsa.review.model.QReview;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReviewResponseDto> getReviewsAllByProductId(Long productId, ReviewRateDto reviewRateDto, Pageable pageable) {
        QReview review = QReview.review;
        QProduct product = QProduct.product;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(review.product.id.eq(productId));

        if (reviewRateDto.getMinRate() != null) {
            builder.and(review.rate.goe(reviewRateDto.getMinRate()));
        }

        if (reviewRateDto.getMaxRate() != null) {
            builder.and(review.rate.goe(reviewRateDto.getMinRate()));
        }

        return new PageImpl<>(
                queryFactory
                        .select(Projections.constructor(ReviewResponseDto.class,
                                review.id,
                                review.rate,
                                review.content,
                                review.imageUrl,
                                review.orderItem.id,
                                review.user.userId,
                                Expressions.numberTemplate(Long.class, "COUNT(*) OVER()")
                        ))
                        .from(review)
                        .leftJoin(product).on(review.product.id.eq(product.id))
                        .where(builder)
                        .orderBy(review.rate.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch(),
                pageable,
                queryFactory
                        .select(review.count())
                        .from(review)
                        .where(builder)
                        .fetch()
                        .size()
        );
    }
}
