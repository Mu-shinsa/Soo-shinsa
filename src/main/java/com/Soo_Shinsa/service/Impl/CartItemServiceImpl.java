package com.Soo_Shinsa.service.Impl;

import java.util.UUID;
import com.Soo_Shinsa.auth.UserDetailsImp;
import com.Soo_Shinsa.constant.Status;
import com.Soo_Shinsa.dto.CartItemResponseDto;
import com.Soo_Shinsa.dto.OrdersResponseDto;
import com.Soo_Shinsa.entity.*;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.CartItemRepository;
import com.Soo_Shinsa.repository.OrdersRepository;
import com.Soo_Shinsa.repository.ProcductOptionRepository;
import com.Soo_Shinsa.repository.UserRepository;
import com.Soo_Shinsa.service.CartItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProcductOptionRepository procductOptionRepository;
    private final OrdersRepository ordersRepository;


    @Transactional
    @Override
    public CartItemResponseDto create(Long optionId,Integer quantity,Long userId) {

        User user = checkUser(userId);
        Optional<ProductOption> findOption = procductOptionRepository.findById(optionId);

        CartItem cartItem = new CartItem(quantity,user,findOption.get());

        return CartItemResponseDto.toDto(cartItem);
    }

    @Transactional
    @Override
    public OrdersResponseDto createOrderFromCart(Long userId) {
        // 사용자 확인
        User user = checkUser(userId);

        // 사용자 카트 항목 조회
        List<CartItem> cartItems = cartItemRepository.findByUserUserId(userId);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("카트에 담긴 상품이 없습니다.");
        }
        String orderNumber = "ORD-" + UUID.randomUUID();
        // Orders 생성
        Orders order = new Orders(orderNumber, BigDecimal.ZERO, Status.ACTIVE, user, new ArrayList<>());

        // CartItem 데이터를 기반으로 OrderItem 생성 및 추가
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProductOption().getProductId();
            Integer quantity = cartItem.getQuantity();

            OrderItem orderItem = new OrderItem(quantity, order, product);
            order.addOrderItem(orderItem);
        }

        // Orders 저장
        ordersRepository.save(order);

        // 카트 비우기
        cartItemRepository.deleteAll(cartItems);

        // OrdersResponseDto로 변환하여 반환
        return OrdersResponseDto.toDto(order);
    }
    @Transactional(readOnly = true)
    @Override
    public CartItemResponseDto findById(Long cartId, Long userId) {

        checkUser(userId);
        CartItem savedCart = findByIdOrElseThrow(cartId);
        return CartItemResponseDto.toDto(savedCart);


    }
    @Transactional(readOnly = true)
    @Override
    public List<CartItemResponseDto> findByAll(Long userId) {
        User findUser = checkUser(userId);


        List<CartItem> allCartItem = cartItemRepository.findAllByUserUserId(findUser.getUserId());

        return allCartItem.stream().map(CartItemResponseDto::toDto).toList();
    }

    @Transactional
    @Override
    public CartItemResponseDto update(Long cartId, Long userId,Integer quantity) {
        checkUser(userId);
        CartItem findCart = findByIdOrElseThrow(cartId);
        findCart.updateCartItem(quantity);

        CartItem saved = cartItemRepository.save(findCart);
        return CartItemResponseDto.toDto(saved);
    }
    @Transactional
    @Override
    public CartItemResponseDto delete(Long cartId, Long userId) {
        checkUser(userId);
        CartItem findCart = findByIdOrElseThrow(cartId);
        cartItemRepository.delete(findCart);
        return CartItemResponseDto.toDto(findCart);

    }
    @Transactional(readOnly = true)
    @Override
    public CartItem findByIdOrElseThrow(Long id) {
        return cartItemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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
