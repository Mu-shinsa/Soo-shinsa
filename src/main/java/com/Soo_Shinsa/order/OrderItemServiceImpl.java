package com.Soo_Shinsa.order;

import com.Soo_Shinsa.exception.InternalServerException;
import com.Soo_Shinsa.order.dto.OrderDateRequestDto;
import com.Soo_Shinsa.order.dto.OrderItemRequestDto;
import com.Soo_Shinsa.order.dto.OrderItemResponseDto;
import com.Soo_Shinsa.order.dto.OrdersResponseDto;
import com.Soo_Shinsa.order.model.OrderItem;
import com.Soo_Shinsa.order.model.Orders;
import com.Soo_Shinsa.product.ProductRepository;
import com.Soo_Shinsa.product.model.Product;
import com.Soo_Shinsa.user.UserRepository;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.utils.EntityValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.Soo_Shinsa.constant.OrdersStatus.BEFOREPAYMENT;
import static com.Soo_Shinsa.exception.ErrorCode.ONLY_BEFORE_PAYMENT;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;

    //오더 아이템 생성
    @Transactional
    @Override
    public OrdersResponseDto createOrderItem(OrderItemRequestDto requestDto, User user) {
        User findUser = userRepository.findByIdOrElseThrow(user.getUserId());
        Orders findOrder = ordersRepository.findById(requestDto.getOrderId()).orElse(null);
        Product product = productRepository.findByIdOrElseThrow(requestDto.getProductId());

        if(findOrder==null) {

            Orders order = new Orders(BigDecimal.ZERO, BEFOREPAYMENT, user);
            ordersRepository.save(order);
            OrderItem orderItem = new OrderItem(
                    requestDto.getQuantity(),
                    order,
                    product

            );
            order.addOrderItem(orderItem);orderItemRepository.save(orderItem);
            Orders saveOrder = ordersRepository.save(order);
            return OrdersResponseDto.toDto(saveOrder);
        }
        if(!findOrder.getStatus().equals(BEFOREPAYMENT)){
            throw new InternalServerException(ONLY_BEFORE_PAYMENT);
        }
        EntityValidator.validateAndOrders(findOrder, findUser.getUserId());
        OrderItem orderItem = new OrderItem(
                requestDto.getQuantity(),
                findOrder,
                product
        );
        findOrder.addOrderItem(orderItem);
        orderItemRepository.save(orderItem);
        Orders saveOrder = ordersRepository.save(findOrder);
        return OrdersResponseDto.toDto(saveOrder);
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
        return orderItemRepository.findByAll(user, dateRequestDto, pageable);
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
