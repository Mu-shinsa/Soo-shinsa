package com.Soo_Shinsa.cartitem;

import com.Soo_Shinsa.cartitem.dto.CartItemRequestDto;
import com.Soo_Shinsa.cartitem.dto.CartItemResponseDto;
import com.Soo_Shinsa.cartitem.model.CartItem;
import com.Soo_Shinsa.product.ProductRepository;
import com.Soo_Shinsa.product.model.Product;
import com.Soo_Shinsa.product.model.ProductOption;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.product.ProductOptionRepository;
import com.Soo_Shinsa.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductOptionRepository productOptionRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public CartItemResponseDto create(User user, CartItemRequestDto requestDto) {

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        List<ProductOption> productOptions = productOptionRepository.findAllByProductId(product.getId());

        CartItem cartItem = CartItem.builder()
                .quantity(requestDto.getQuantity())
                .user(user)
                .product(product)
                .build();

        cartItemRepository.save(cartItem);

        return CartItemResponseDto.toDto(cartItem, productOptions);
    }

    @Override
    public CartItemResponseDto findById(Long cartId, User user) {
        // 사용자 정보 가져오기
        User userId = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));

        CartItem cartItem = cartItemRepository.findById(cartId, "해당 id값이 존재하지 않습니다.");

        //사용자의 카트인지 확인
        userId.validateCartItemUser(cartItem);

        List<ProductOption> productOptions = productOptionRepository.findAllByProductId(cartItem.getProduct().getId());

        return CartItemResponseDto.toDto(cartItem, productOptions);
    }

    @Override
    public Page<CartItemResponseDto> findByAll(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));

        Page<CartItem> allCartItem = cartItemRepository.findAllByUserUserId(user.getUserId(), pageable);

        return allCartItem.map(cartItem -> {
            List<ProductOption> productOptions = productOptionRepository.findAllByProductId(cartItem.getProduct().getId());
            return CartItemResponseDto.toDto(cartItem, productOptions);
        });
    }

    @Transactional
    @Override
    public CartItemResponseDto update(Long cartId, User user, Integer quantity) {
        User userId = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));

        CartItem cartItem = cartItemRepository.findById(cartId, "해당 id값이 존재하지 않습니다.");

        userId.validateCartItemUser(cartItem);

        cartItem.updateCartItem(quantity);


        // 상품 옵션 조회
        List<ProductOption> productOptions = productOptionRepository.findAllByProductId(cartItem.getProduct().getId());

        return CartItemResponseDto.toDto(cartItem, productOptions);
    }


    @Transactional
    @Override
    public void delete(Long cartId, User user) {
        User userId = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));

        CartItem cartItem = cartItemRepository.findById(cartId, "해당 id값이 존재하지 않습니다.");

        userId.validateCartItemUser(cartItem);

        cartItemRepository.delete(cartItem);
    }
}
