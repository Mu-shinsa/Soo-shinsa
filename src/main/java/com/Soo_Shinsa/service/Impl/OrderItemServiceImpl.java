package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.auth.UserDetailsImp;
import com.Soo_Shinsa.dto.OrderItemResponseDto;
import com.Soo_Shinsa.entity.*;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.*;
import com.Soo_Shinsa.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository ;
    private final OrdersRepository ordersRepository;

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

        return OrderItemResponseDto.toDto(orderItem);
    }

    @Override
    public OrderItemResponseDto findById(Long orderItemsId, Long userId) {
        checkUser(userId);

        OrderItem byIdOrElseThrow = findByIdOrElseThrow(orderItemsId);
        return OrderItemResponseDto.toDto(byIdOrElseThrow);
    }

    @Override
    public List<OrderItemResponseDto> findByAll(Long userId) {
        checkUser(userId);

        List<OrderItem> orderItems = orderItemRepository.findAllByUserIdWithFetchJoin(userId);
        return orderItems.stream().map(OrderItemResponseDto::toDto).toList();
    }

    @Override
    public OrderItemResponseDto update(Long orderItemsId, Long userId, Integer quantity) {
        checkUser(userId);
        OrderItem findOrder = findByIdOrElseThrow(orderItemsId);
        findOrder.updateOrderItem(quantity);
        OrderItem save = orderItemRepository.save(findOrder);
        return OrderItemResponseDto.toDto(save);
    }

    @Override
    public void delete(Long orderItemsId, Long userId) {
        checkUser(userId);
        OrderItem find = findByIdOrElseThrow(orderItemsId);
        orderItemRepository.delete(find);

    }


    @Override
    public OrderItem findByIdOrElseThrow(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }




    private User checkUser(Long userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        User user = userDetails.getUser();

        Optional<User> loginId = userRepository.findById(user.getUserId());

        if(!loginId.get().getUserId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        return user;
    }
}
