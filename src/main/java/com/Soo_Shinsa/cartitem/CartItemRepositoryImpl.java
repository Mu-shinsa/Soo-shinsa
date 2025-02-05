package com.Soo_Shinsa.cartitem;

import com.Soo_Shinsa.cartitem.dto.CartItemDateRequestDto;
import com.Soo_Shinsa.cartitem.dto.CartItemResponseDto;
import com.Soo_Shinsa.cartitem.model.QCartItem;
import com.Soo_Shinsa.product.model.QProductOption;
import com.Soo_Shinsa.user.model.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;

@RequiredArgsConstructor
public class CartItemRepositoryImpl implements CartItemCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CartItemResponseDto> findByAllCartItem(User user, CartItemDateRequestDto requestDto, Pageable pageable) {
        QCartItem cartItem = QCartItem.cartItem;
        QProductOption productOption = QProductOption.productOption;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cartItem.user.userId.eq(user.getUserId()));

        if (requestDto.getStartDate() != null) {
            builder.and(cartItem.createdAt.goe(Timestamp.valueOf(requestDto.getStartDate().atStartOfDay())));
        }

        if (requestDto.getEndDate() != null) {
            builder.and(cartItem.createdAt.loe(Timestamp.valueOf(requestDto.getEndDate().atStartOfDay())));
        }

        return new PageImpl<>(
                queryFactory
                        .select(Projections.constructor(CartItemResponseDto.class,
                                cartItem.id,
                                cartItem.quantity,
                                cartItem.product.id,
                                cartItem.product.name,
                                cartItem.product.price,
                                cartItem.productOption.id,
                                Expressions.numberTemplate(Long.class, "COUNT(*) OVER()")
                        ))
                        .from(cartItem)
                        .leftJoin(productOption).on(cartItem.product.id.eq(productOption.product.id))
                        .where(builder)
                        .orderBy(cartItem.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch(),
                pageable,
                queryFactory
                        .select(Expressions.numberTemplate(Long.class, "COUNT(*) OVER()"))
                        .from(cartItem)
                        .where(builder)
                        .fetch()
                        .size()// Fetch count
        );

    }
}
