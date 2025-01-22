package com.Soo_Shinsa.service.Impl;


import com.Soo_Shinsa.dto.order.OrderItemRequestDto;
import com.Soo_Shinsa.dto.order.OrderItemResponseDto;
import com.Soo_Shinsa.model.OrderItem;
import com.Soo_Shinsa.model.Orders;
import com.Soo_Shinsa.model.Product;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.*;
import com.Soo_Shinsa.service.OrderItemService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository ;
    private final OrdersRepository ordersRepository;
    //오더 아이템 생성
    @Transactional
    public OrderItemResponseDto createOrderItem(OrderItemRequestDto requestDto) {

        // 주문 조회
        Orders order = ordersRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("주문이 없습니다.: " + requestDto.getOrderId()));

        // 상품 조회
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품이 없습니다.: " + requestDto.getProductId()));
        // OrderItem 생성 및 저장
        OrderItem orderItem = new OrderItem(
                requestDto.getQuantity(),
                order,
                product
        );

        // OrderItem을 Order에 추가
        order.addOrderItem(orderItem);

        // OrderItem 저장
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        // 변경된 Order도 저장 (CascadeType.ALL로 인해 필요하지 않을 수도 있음)
        ordersRepository.save(order);

        return OrderItemResponseDto.toDto(savedOrderItem);
    }
    //오더 아이템 찾아오고 dto로 변환
    @Transactional(readOnly = true)
    @Override
    public OrderItemResponseDto findById(Long orderItemsId) {
        //오더 아이템을 찾아옴
        OrderItem byIdOrElseThrow = findByIdOrElseThrow(orderItemsId);
        //dto로 변환
        return OrderItemResponseDto.toDto(byIdOrElseThrow);
    }


    //유저 오더아이템들을 찾아옴
    @Transactional(readOnly = true)
    @Override
    public List<OrderItemResponseDto> findByAll(User user) {

        //회원의 모든 아이템 오더를 리스트르 받아옴
        List<OrderItem> orderItems = orderItemRepository.findAllByUserIdWithFetchJoin(user.getUserId());
        //dto로 변환
        return orderItems.stream().map(OrderItemResponseDto::toDto).toList();
    }
    //오더 아이템 수정
    @Transactional
    @Override
    public OrderItemResponseDto update(Long orderItemsId, Integer quantity) {
        //오더 아이템을 찾아옴
        OrderItem findOrder = findByIdOrElseThrow(orderItemsId);
        //찾아옴 오더아이템 수량을 변경
        findOrder.updateOrderItem(quantity);
        OrderItem save = orderItemRepository.save(findOrder);
        //dto로 변환
        return OrderItemResponseDto.toDto(save);
    }
    //오더 아이템 삭제
    @Override
    @Transactional
    public OrderItemResponseDto delete(Long orderItemsId) {

        // OrderItem 조회
        OrderItem find = findByIdOrElseThrow(orderItemsId);

        // Orders 조회
        Orders order = ordersRepository.findById(find.getOrder().getId())
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다"));
        // OrderItem 삭제
        order.removeOrderItem(find); // 연관 관계에서 제거
        ordersRepository.delete(order);// Order 저장 (OrderItem 자동 삭제)
        //dto 변환
        return OrderItemResponseDto.toDto(find);

    }
    //오더 아이템을 찾아옴
    @Transactional(readOnly = true)
    @Override
    public OrderItem findByIdOrElseThrow(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
