package com.Soo_Shinsa.order;


import com.Soo_Shinsa.order.dto.OrderItemRequestDto;
import com.Soo_Shinsa.order.dto.OrderItemResponseDto;
import com.Soo_Shinsa.order.model.OrderItem;
import com.Soo_Shinsa.order.model.Orders;
import com.Soo_Shinsa.product.ProductRepository;
import com.Soo_Shinsa.product.model.Product;
import com.Soo_Shinsa.user.UserRepository;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.utils.EntityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository ;
    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;
    //오더 아이템 생성
    @Transactional
    public OrderItemResponseDto createOrderItem(OrderItemRequestDto requestDto,User user) {

        User findUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));
        Orders findOrder = ordersRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("주문이 없습니다.: " + requestDto.getOrderId()));

        EntityValidator.validateAndOrders(findOrder,findUser.getUserId());
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품이 없습니다.: " + requestDto.getProductId()));
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
    public OrderItemResponseDto findById(Long orderItemsId,User user) {
        User findUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));
        OrderItem findOrderItem = orderItemRepository.findByIdOrElseThrow(orderItemsId);

        EntityValidator.validateAndOrderItem(findOrderItem,findUser.getUserId());
        return OrderItemResponseDto.toDto(findOrderItem);
    }


    //유저 오더아이템들을 찾아옴
    @Override
    public Page<OrderItemResponseDto> findByAll(User user, Pageable pageable) {

        Page<OrderItem> orderItems = orderItemRepository.findAllByUserIdWithFetchJoin(user.getUserId(),pageable);
        return orderItems.map(OrderItemResponseDto::toDto);
    }

    //오더 아이템 수정
    @Transactional
    @Override
    public OrderItemResponseDto update(Long orderItemsId, Integer quantity,User user) {
        User findUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));
        OrderItem findOrderItem = orderItemRepository.findByIdOrElseThrow(orderItemsId);


        EntityValidator.validateAndOrderItem(findOrderItem,findUser.getUserId());
        findOrderItem.updateOrderItem(quantity);
        OrderItem save = orderItemRepository.save(findOrderItem);
        return OrderItemResponseDto.toDto(save);
    }

    //오더 아이템 삭제
    @Transactional
    @Override
    public OrderItemResponseDto delete(Long orderItemsId,User user) {
        User findUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));

        OrderItem findOrderItem = orderItemRepository.findByIdOrElseThrow(orderItemsId);
        EntityValidator.validateAndOrderItem(findOrderItem,findUser.getUserId());

        Orders order = ordersRepository.findById(findOrderItem.getOrder().getId())
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다"));

        order.removeOrderItem(findOrderItem); // 연관 관계에서 제거
        ordersRepository.delete(order);// Order 저장 (OrderItem 자동 삭제)

        return OrderItemResponseDto.toDto(findOrderItem);

    }


}
