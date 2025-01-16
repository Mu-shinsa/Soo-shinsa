package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.auth.UserDetailsImp;
import com.Soo_Shinsa.dto.OrderItemResponseDto;
import com.Soo_Shinsa.entity.*;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.*;
import com.Soo_Shinsa.service.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository ;
    private final OrdersRepository ordersRepository;

    @Transactional
    @Override
    public OrderItemResponseDto createOrderItem(Long orderId, Long productId, Integer quantity,Long userId) {
        User user = checkUser(userId);
        // 주문 확인
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다"));

        // Order와 User의 일치 확인
        if (!order.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("주문한 회원과 일치하지 않습니다");
        }

        // 상품 확인
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        OrderItem orderItem = new OrderItem(quantity, order, product);

        order.addOrderItem(orderItem);
        ordersRepository.save(order);
        return OrderItemResponseDto.toDto(orderItem);
    }
    @Transactional
    @Override
    public OrderItemResponseDto findById(Long orderItemsId, Long userId) {
        checkUser(userId);

        OrderItem byIdOrElseThrow = findByIdOrElseThrow(orderItemsId);
        return OrderItemResponseDto.toDto(byIdOrElseThrow);
    }
    @Transactional
    @Override
    public List<OrderItemResponseDto> findByAll(Long userId) {
        checkUser(userId);

        List<OrderItem> orderItems = orderItemRepository.findAllByUserIdWithFetchJoin(userId);
        return orderItems.stream().map(OrderItemResponseDto::toDto).toList();
    }
    @Transactional
    @Override
    public OrderItemResponseDto update(Long orderItemsId, Long userId, Integer quantity) {
        checkUser(userId);
        OrderItem findOrder = findByIdOrElseThrow(orderItemsId);
        findOrder.updateOrderItem(quantity);
        OrderItem save = orderItemRepository.save(findOrder);
        return OrderItemResponseDto.toDto(save);
    }

    @Override
    @Transactional
    public OrderItemResponseDto delete(Long orderItemsId, Long userId) {
        checkUser(userId);

        // OrderItem 조회
        OrderItem find = findByIdOrElseThrow(orderItemsId);

        // Orders 조회
        Orders order = ordersRepository.findById(find.getOrder().getId())
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다"));
        OrderItem save = orderItemRepository.save(find);
        // OrderItem 삭제
        order.removeOrderItem(find); // 연관 관계에서 제거
        ordersRepository.save(order);// Order 저장 (OrderItem 자동 삭제)
        return OrderItemResponseDto.toDto(save);

    }


    @Override
    public OrderItem findByIdOrElseThrow(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }




    private User checkUser(Long userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        User user = userDetails.getUser();

        User loginId = userRepository.findById(user.getUserId()).orElseThrow(() -> new EntityNotFoundException("해당 id값이 존재하지 않습니다."));;

        if(!loginId.getUserId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        return user;
    }
}
