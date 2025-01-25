package com.Soo_Shinsa.order;



import com.Soo_Shinsa.order.dto.OrderItemRequestDto;
import com.Soo_Shinsa.order.dto.OrderItemResponseDto;
import com.Soo_Shinsa.order.model.OrderItem;
import com.Soo_Shinsa.order.model.Orders;
import com.Soo_Shinsa.product.ProductRepository;
import com.Soo_Shinsa.product.model.Product;
import com.Soo_Shinsa.user.UserRepository;
import com.Soo_Shinsa.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        // 주문 조회
        Orders findOrder = ordersRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("주문이 없습니다.: " + requestDto.getOrderId()));

        findUser.validateAndOrders(findOrder);
        // 상품 조회
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품이 없습니다.: " + requestDto.getProductId()));
        // OrderItem 생성 및 저장
        OrderItem orderItem = new OrderItem(
                requestDto.getQuantity(),
                findOrder,
                product
        );

        // OrderItem을 Order에 추가
        findOrder.addOrderItem(orderItem);

        // OrderItem 저장
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        // 변경된 Order도 저장 (CascadeType.ALL로 인해 필요하지 않을 수도 있음)
        ordersRepository.save(findOrder);

        return OrderItemResponseDto.toDto(savedOrderItem);
    }
    //오더 아이템 찾아오고 dto로 변환
    @Override
    public OrderItemResponseDto findById(Long orderItemsId,User user) {
        //오더 아이템을 찾아옴
        User findUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));
        OrderItem findOrderItem = findByIdOrElseThrow(orderItemsId);

        findUser.validateAndOrderItem(findOrderItem);
        //dto로 변환
        return OrderItemResponseDto.toDto(findOrderItem);
    }


    //유저 오더아이템들을 찾아옴
    @Override
    public Page<OrderItemResponseDto> findByAll(User user, Pageable pageable) {

        //회원의 모든 아이템 오더를 리스트르 받아옴
        Page<OrderItem> orderItems = orderItemRepository.findAllByUserIdWithFetchJoin(user.getUserId(),pageable);
        //dto로 변환
//        return orderItems.stream().map(OrderItemResponseDto::toDto).toList();
        return orderItems.map(OrderItemResponseDto::toDto);
    }
    //오더 아이템 수정
    @Transactional
    @Override
    public OrderItemResponseDto update(Long orderItemsId, Integer quantity,User user) {
        User findUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));
        //오더 아이템을 찾아옴
        OrderItem findOrderItem = findByIdOrElseThrow(orderItemsId);


        findUser.validateAndOrderItem(findOrderItem);
        //찾아옴 오더아이템 수량을 변경
        findOrderItem.updateOrderItem(quantity);
        OrderItem save = orderItemRepository.save(findOrderItem);
        //dto로 변환
        return OrderItemResponseDto.toDto(save);
    }
    //오더 아이템 삭제
    @Transactional
    @Override

    public OrderItemResponseDto delete(Long orderItemsId,User user) {
        User findUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));
        // OrderItem 조회
        OrderItem findOrderItem = findByIdOrElseThrow(orderItemsId);
        findUser.validateAndOrderItem(findOrderItem);
        // Orders 조회
        Orders order = ordersRepository.findById(findOrderItem.getOrder().getId())
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다"));
        // OrderItem 삭제
        order.removeOrderItem(findOrderItem); // 연관 관계에서 제거
        ordersRepository.delete(order);// Order 저장 (OrderItem 자동 삭제)
        //dto 변환
        return OrderItemResponseDto.toDto(findOrderItem);

    }
    //오더 아이템을 찾아옴
    @Override
    public OrderItem findByIdOrElseThrow(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
