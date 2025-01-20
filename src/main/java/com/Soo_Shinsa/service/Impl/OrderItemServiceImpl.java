package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.auth.UserDetailsImp;
import com.Soo_Shinsa.dto.OrderItemResponseDto;
import com.Soo_Shinsa.entity.*;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.*;
import com.Soo_Shinsa.service.OrderItemService;
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
    //오더 아이템 생성
    @Transactional
    @Override
    public OrderItemResponseDto createOrderItem(Long orderId,Long productId, Integer quantity,Long userId,User user) {
        // 주문을 찾아옴 없을시 예외 던짐
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다"));


        // 상품 확인 찾아옴 없을시 예외 던짐
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        //오더 아이템 생성
        OrderItem orderItem = new OrderItem(quantity, order, product);
        //오더에 오더 아이템을 담음
        order.addOrderItem(orderItem);
        ordersRepository.save(order);
        //dto로 변환
        return OrderItemResponseDto.toDto(orderItem);
    }
    //오더 아이템 찾아오고 dto로 변환
    @Transactional(readOnly = true)
    @Override
    public OrderItemResponseDto findById(Long orderItemsId, Long userId,User user) {

        //오더 아이템을 찾아옴
        OrderItem byIdOrElseThrow = findByIdOrElseThrow(orderItemsId);
        //dto로 변환
        return OrderItemResponseDto.toDto(byIdOrElseThrow);
    }
    //유저 오더아이템들을 찾아옴
    @Transactional(readOnly = true)
    @Override
    public List<OrderItemResponseDto> findByAll(Long userId,User user) {


        //회원의 모든 아이템 오더를 리스트르 받아옴
        List<OrderItem> orderItems = orderItemRepository.findAllByUserIdWithFetchJoin(userId);
        //dto로 변환
        return orderItems.stream().map(OrderItemResponseDto::toDto).toList();
    }
    //오더 아이템 수정
    @Transactional
    @Override
    public OrderItemResponseDto update(Long orderItemsId, Long userId, Integer quantity,User user) {

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
    public OrderItemResponseDto delete(Long orderItemsId, Long userId,User user) {


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
