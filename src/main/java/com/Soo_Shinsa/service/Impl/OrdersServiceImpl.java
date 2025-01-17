package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.auth.UserDetailsImp;
import com.Soo_Shinsa.constant.Status;
import com.Soo_Shinsa.dto.OrdersResponseDto;
import com.Soo_Shinsa.entity.OrderItem;
import com.Soo_Shinsa.entity.Orders;
import com.Soo_Shinsa.entity.Product;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.*;
import com.Soo_Shinsa.service.OrdersService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;

    @Transactional(readOnly = true)
    @Override
    public OrdersResponseDto getOrderById(Long orderId,Long userId) {
        checkUser(userId);
        // Orders와 OrderItems를 함께 조회
        Orders order = ordersRepository.findOrderWithItems(orderId);

        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Orders를 찾을 수 없습니다");
        }
        // OrdersResponseDto의 toDto 메서드 사용
        return OrdersResponseDto.toDto(order);
    }
    @Transactional
    @Override
    public OrdersResponseDto createSingleProductOrder(Long userId, Long productId, Integer quantity) {
        // 사용자 조회
        User user = checkUser(userId);

        // 상품 확인
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        String orderNumber = "ORD-" + UUID.randomUUID();
        // Orders 생성
        Orders order = new Orders(orderNumber, BigDecimal.ZERO, Status.ACTIVE, user, new ArrayList<>());

        // OrderItem 생성
        OrderItem orderItem = new OrderItem(quantity, order, product);
        order.addOrderItem(orderItem);

        // Orders 저장
        ordersRepository.save(order);

        // OrdersResponseDto로 변환 후 반환
        return OrdersResponseDto.toDto(order);
    }
    @Transactional(readOnly = true)
    protected User checkUser(Long userId){
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
