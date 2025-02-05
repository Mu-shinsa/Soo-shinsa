package com.Soo_Shinsa.order;

import com.Soo_Shinsa.order.dto.OrderDateRequestDto;
import com.Soo_Shinsa.order.dto.OrdersResponseDto;
import com.Soo_Shinsa.order.model.QOrders;
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
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<OrdersResponseDto> getAllByUserId(User user, OrderDateRequestDto dateRequestDto, Pageable pageable) {
        QOrders orders = QOrders.orders;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(orders.user.userId.eq(user.getUserId()));

        if (dateRequestDto.getStartDate() != null) {
            builder.and(orders.createdAt.goe(Timestamp.valueOf(dateRequestDto.getStartDate().atStartOfDay())));
        }

        if (dateRequestDto.getEndDate() != null) {
            builder.and(orders.createdAt.loe(Timestamp.valueOf(dateRequestDto.getEndDate().atStartOfDay())));
        }

        return new PageImpl<>(
                queryFactory
                        .select(Projections.constructor(OrdersResponseDto.class,
                                orders.id,
                                orders.totalPrice,
                                orders.status,
                                orders.user.userId,
                                orders.orderItems,
                                Expressions.numberTemplate(Long.class, "COUNT(*) OVER()")
                        ))
                        .from(orders)
                        .where(builder)
                        .orderBy(orders.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch(),
                pageable,
                queryFactory
                        .select(orders.count())
                        .from(orders)
                        .where(builder)
                        .fetch()
                        .size()
        );
    }
}
