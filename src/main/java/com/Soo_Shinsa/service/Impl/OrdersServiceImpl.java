package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.constant.Status;
import com.Soo_Shinsa.dto.OrderItemRequestDto;
import com.Soo_Shinsa.dto.OrdersRequestDto;
import com.Soo_Shinsa.dto.OrdersResponseDto;
import com.Soo_Shinsa.entity.CartItem;
import com.Soo_Shinsa.entity.OrderItem;
import com.Soo_Shinsa.entity.Orders;
import com.Soo_Shinsa.entity.Product;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.*;
import com.Soo_Shinsa.service.OrdersService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;


    //주문을 찾아오고 주문이 없다면 예외를 던지고 dto로 변환
    @Transactional(readOnly = true)
    @Override
    public OrdersResponseDto getOrderById(Long orderId) {

        //오더를 찾아옴
        Orders orderWithItems = ordersRepository.findOrderWithItems(orderId);

        //주문이 없을시 예외 던짐
        if (orderWithItems == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Orders를 찾을 수 없습니다");
        }
        // OrdersResponseDto의 toDto 메서드 사용
        return OrdersResponseDto.toDto(orderWithItems);
    }

    //단일 상품 구매
    //상품을 찾아와서 주문번호를 생성 후 주문을 만들고 거기에 주문아이템에 물건을 담음
//    @Transactional
//    @Override
//    public OrdersResponseDto createSingleProductOrder(Long userId, Long productId, Integer quantity, User user) {
//
//        //상품을 찾아와서 옴
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
//        //주문번호를 생성 후 주문을 만들고
//        // Orders 생성
//        Orders order = new Orders(product.getPrice().multiply(BigDecimal.valueOf(quantity)), Status.ACTIVE, user, new ArrayList<>());
//
//
//        //주문아이템을생성
//        OrderItem orderItem = new OrderItem(quantity, order, product);
//        //오더에 오더아이템을 담음
//        order.addOrderItem(orderItem);
//
//        // Orders 저장함
//        ordersRepository.save(order);
//
//        // OrdersResponseDto로 변환 후 반환
//        return OrdersResponseDto.toDto(order);
//    }

    @Transactional
    public OrdersResponseDto createOrderFromCart(Long userId,User user) {
        // 사용자 확인
        // 사용자 카트 항목 조회
        List<CartItem> cartItems = cartItemRepository.findByUserUserId(userId);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("카트에 담긴 상품이 없습니다.");
        }

        // Orders 생성
        Orders order = new Orders(Status.ACTIVE, user, new ArrayList<>());

        // CartItem 데이터를 기반으로 OrderItem 생성 및 추가
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProductOption().getProduct();
            Integer quantity = cartItem.getQuantity();

            // OrderItem 생성
            OrderItem orderItem = new OrderItem(quantity, order, product);
            order.addOrderItem(orderItem); // Order에 추가 및 총 금액 계산
        }

        // Orders 저장
        ordersRepository.save(order);

        // 카트 비우기
        cartItemRepository.deleteAll(cartItems);

        // OrdersResponseDto로 변환하여 반환
        return OrdersResponseDto.toDto(order);
    }

    @Transactional
    public OrdersResponseDto createOrder(Long userId,List<OrderItemRequestDto> orderItems) {
        // 사용자 정보 가져오기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));

        Orders order = new Orders(BigDecimal.ZERO, Status.ACTIVE, user, new ArrayList<>());

        // OrderItems 추가
        orderItems.forEach(orderItemDto -> {
            Product product = productRepository.findById(orderItemDto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));
            OrderItem orderItem = new OrderItem(
                    orderItemDto.getQuantity(),
                    order,
                    product
            );
            order.addOrderItem(orderItem);
        });

        // 주문 저장
        Orders savedOrder = ordersRepository.save(order);

        // ResponseDto로 변환
        return OrdersResponseDto.toDto(savedOrder);
    }

}
