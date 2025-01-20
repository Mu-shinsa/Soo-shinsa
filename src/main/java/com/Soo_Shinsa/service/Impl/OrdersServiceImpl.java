package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.constant.Status;
import com.Soo_Shinsa.dto.OrdersResponseDto;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;


    //주문을 찾아오고 주문이 없다면 예외를 던지고 dto로 변환
    @Transactional(readOnly = true)
    @Override
    public OrdersResponseDto getOrderById(Long orderId, Long userId, User user) {
        //오더를 찾아옴
        Orders order = ordersRepository.findOrderWithItems(orderId);

        //주문이 없을시 예외 던짐
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Orders를 찾을 수 없습니다");
        }
        // OrdersResponseDto의 toDto 메서드 사용
        return OrdersResponseDto.toDto(order);
    }

    //단일 상품 구매
    //상품을 찾아와서 주문번호를 생성 후 주문을 만들고 거기에 주문아이템에 물건을 담음
    @Transactional
    @Override
    public OrdersResponseDto createSingleProductOrder(Long userId, Long productId, Integer quantity, User user) {

        //상품을 찾아와서 옴
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        //주문번호를 생성 후 주문을 만들고
        String orderNumber = "ORD-" + UUID.randomUUID();
        // Orders 생성
        Orders order = new Orders(orderNumber, product.getPrice().multiply(BigDecimal.valueOf(quantity)), Status.ACTIVE, user, new ArrayList<>());


        //주문아이템을생성
        OrderItem orderItem = new OrderItem(quantity, order, product);
        //오더에 오더아이템을 담음
        order.addOrderItem(orderItem);

        // Orders 저장함
        ordersRepository.save(order);

        // OrdersResponseDto로 변환 후 반환
        return OrdersResponseDto.toDto(order);
    }

}
