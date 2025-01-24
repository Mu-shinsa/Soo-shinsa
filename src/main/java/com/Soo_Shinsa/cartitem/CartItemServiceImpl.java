package com.Soo_Shinsa.cartitem;


import com.Soo_Shinsa.cartitem.dto.CartItemResponseDto;
import com.Soo_Shinsa.order.dto.OrdersResponseDto;
import com.Soo_Shinsa.product.model.ProductOption;

import com.Soo_Shinsa.product.ProductOptionRepository;

import com.Soo_Shinsa.utils.user.UserRepository;
import com.Soo_Shinsa.utils.user.model.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductOptionRepository productOptionRepository;
    private final UserRepository userRepository;

    //카트아이템을 생성
    @Transactional
    @Override
    public CartItemResponseDto create(Long optionId,Integer quantity,Long userId) {
        // 사용자 정보 가져오기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));
        //상품 옵션을 찾아옴
        ProductOption findOption = productOptionRepository.findById(optionId).orElseThrow(() -> new EntityNotFoundException("해당 id값이 존재하지 않습니다."));
        //카트를 생성
        CartItem cartItem = new CartItem(quantity,user,findOption);

        cartItemRepository.save(cartItem);

        return CartItemResponseDto.toDto(cartItem);
    }

//    //카트아이템 찾아옴
    @Transactional(readOnly = true)
    @Override
    public CartItemResponseDto findById(Long cartId, Long userId) {
        // 사용자 정보 가져오기
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));

//        //카트 아이템 찾아옴
        CartItem savedCart = findByIdOrElseThrow(cartId);
        //저장
        return CartItemResponseDto.toDto(savedCart);
    }
    //유저의 카트들을 다 가져옴
    @Transactional(readOnly = true)
    @Override
    public Page<CartItemResponseDto> findByAll(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));

        //로그인 유저의 카트목록들을 다 가져옴
        Page<CartItem> allCartItem = cartItemRepository.findAllByUserUserId(user.getUserId(),pageable);
        //dto 저장

        return allCartItem.map(CartItemResponseDto::toDto);
    }

    //카트 수정
    @Transactional
    @Override
    public CartItemResponseDto update(Long cartId, Long userId,Integer quantity) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));
        //카트 아이템 검색
        CartItem findCart = findByIdOrElseThrow(cartId);
        //가져온 카트 아이템 수량 변경
        findCart.updateCartItem(quantity);
        //저장
        CartItem saved = cartItemRepository.save(findCart);
        //dto 변환
        return CartItemResponseDto.toDto(saved);
    }
    //카트 아이템 삭제
    @Transactional
    @Override
    public CartItemResponseDto delete(Long cartId, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));
        //카트를 가져옴
        CartItem findCart = findByIdOrElseThrow(cartId);
        //삭제함
        cartItemRepository.delete(findCart);
        //dto로 변환
        return CartItemResponseDto.toDto(findCart);
    }

    //카트 아이템을 찾아옴
    @Transactional(readOnly = true)
    @Override
    public CartItem findByIdOrElseThrow(Long id) {
        return cartItemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
