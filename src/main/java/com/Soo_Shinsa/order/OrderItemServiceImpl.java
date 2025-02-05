package com.Soo_Shinsa.order;

import com.Soo_Shinsa.constant.ProductStatus;
import com.Soo_Shinsa.exception.ErrorCode;
import com.Soo_Shinsa.exception.InternalServerException;
import com.Soo_Shinsa.order.dto.OrderDateRequestDto;
import com.Soo_Shinsa.order.dto.OrderItemRequestDto;
import com.Soo_Shinsa.order.dto.OrderItemResponseDto;
import com.Soo_Shinsa.order.model.OrderItem;
import com.Soo_Shinsa.order.model.Orders;
import com.Soo_Shinsa.order.model.QOrderItem;
import com.Soo_Shinsa.product.ProductRepository;
import com.Soo_Shinsa.product.model.Product;
import com.Soo_Shinsa.user.UserRepository;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.utils.EntityValidator;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;
    private final JPAQueryFactory queryFactory;

    //오더 아이템 생성
    @Transactional
    @Override
    public OrderItemResponseDto createOrderItem(OrderItemRequestDto requestDto, User user) {

        User findUser = userRepository.findByIdOrElseThrow(user.getUserId());
        Orders findOrder = ordersRepository.findByIdOrElseThrow(requestDto.getOrderId());

        EntityValidator.validateAndOrders(findOrder, findUser.getUserId());
        Product product = productRepository.findByIdOrElseThrow(requestDto.getProductId());
        if (product.getProductStatus().equals(ProductStatus.SOLD_OUT) || product.getProductStatus().equals(ProductStatus.UNAVAILABLE)) {
            throw new InternalServerException(ErrorCode.CAN_NOT_USE_PRODUCT);
        }
        OrderItem orderItem = new OrderItem(
                requestDto.getQuantity(),
                findOrder,
                product
        );

        findOrder.addOrderItem(orderItem);

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);


        ordersRepository.save(findOrder);

        return OrderItemResponseDto.toDto(savedOrderItem);
    }

    @Override
    public OrderItemResponseDto findById(Long orderItemsId, User user) {
        User findUser = userRepository.findByIdOrElseThrow(user.getUserId());
        OrderItem findOrderItem = orderItemRepository.findByIdOrElseThrow(orderItemsId);

        EntityValidator.validateAndOrderItem(findOrderItem, findUser.getUserId());
        return OrderItemResponseDto.toDto(findOrderItem);
    }


    //유저 오더아이템들을 찾아옴
    @Override
    public Page<OrderItemResponseDto> findByAll(User user, OrderDateRequestDto dateRequestDto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        QOrderItem orderItem = QOrderItem.orderItem;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(orderItem.order.user.userId.eq(user.getUserId()));

        if (dateRequestDto.getStartDate() != null) {
            builder.and(orderItem.order.createdAt.goe(Timestamp.valueOf(dateRequestDto.getStartDate().atStartOfDay())));
        }

        if (dateRequestDto.getEndDate() != null) {
            builder.and(orderItem.order.createdAt.loe(Timestamp.valueOf(dateRequestDto.getEndDate().atTime(23, 59, 59))));
        }

        List<OrderItem> orderItems =
                queryFactory.selectFrom(orderItem)
                        .where(builder)
                        .orderBy(orderItem.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        long total = queryFactory
                .select(orderItem.count())
                .from(orderItem)
                .where(builder)
                .fetch()
                .size();

        List<OrderItemResponseDto> orderItemResponseDtos = orderItems.stream()
                .map(OrderItemResponseDto::toDto)
                .toList();

        return new PageImpl<>(orderItemResponseDtos, pageable, total);
    }

    //오더 아이템 수정
    @Transactional
    @Override
    public OrderItemResponseDto update(Long orderItemsId, Integer quantity, User user) {
        User findUser = userRepository.findByIdOrElseThrow(user.getUserId());
        OrderItem findOrderItem = orderItemRepository.findByIdOrElseThrow(orderItemsId);


        EntityValidator.validateAndOrderItem(findOrderItem, findUser.getUserId());
        findOrderItem.updateOrderItem(quantity);
        OrderItem save = orderItemRepository.save(findOrderItem);
        return OrderItemResponseDto.toDto(save);
    }

    //오더 아이템 삭제
    @Transactional
    @Override
    public OrderItemResponseDto delete(Long orderItemsId, User user) {
        User findUser = userRepository.findByIdOrElseThrow(user.getUserId());

        OrderItem findOrderItem = orderItemRepository.findByIdOrElseThrow(orderItemsId);
        EntityValidator.validateAndOrderItem(findOrderItem, findUser.getUserId());

        Orders order = ordersRepository.findByIdOrElseThrow(findOrderItem.getOrder().getId());

        order.removeOrderItem(findOrderItem); // 연관 관계에서 제거
        ordersRepository.delete(order);// Order 저장 (OrderItem 자동 삭제)

        return OrderItemResponseDto.toDto(findOrderItem);

    }
}
