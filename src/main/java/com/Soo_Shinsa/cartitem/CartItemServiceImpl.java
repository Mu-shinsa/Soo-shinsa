package com.Soo_Shinsa.cartitem;

import com.Soo_Shinsa.cartitem.dto.CartItemDateRequestDto;
import com.Soo_Shinsa.cartitem.dto.CartItemRequestDto;
import com.Soo_Shinsa.cartitem.dto.CartItemResponseDto;
import com.Soo_Shinsa.cartitem.model.CartItem;
import com.Soo_Shinsa.constant.ProductStatus;
import com.Soo_Shinsa.exception.ErrorCode;
import com.Soo_Shinsa.exception.InternalServerException;
import com.Soo_Shinsa.product.ProductOptionRepository;
import com.Soo_Shinsa.product.ProductRepository;
import com.Soo_Shinsa.product.model.Product;
import com.Soo_Shinsa.product.model.ProductOption;
import com.Soo_Shinsa.user.UserRepository;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.utils.EntityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

        Product product = productRepository.findByIdOrElseThrow(requestDto.getProductId());

        if (product.getProductStatus().equals(ProductStatus.SOLD_OUT) || product.getProductStatus().equals(ProductStatus.UNAVAILABLE)) {
            throw new InternalServerException(ErrorCode.CAN_NOT_USE_PRODUCT);
        }

        List<ProductOption> productOptions = productOptionRepository.findProductOptionByProductId(product.getId());

        CartItem cartItem = CartItem.builder()
                .quantity(requestDto.getQuantity())
                .user(user)
                .product(product)
                .productOption(productOptions.get(0))
                .build();

        cartItemRepository.save(cartItem);

        return CartItemResponseDto.toDto(cartItem, productOptions);
    }

    @Override
    public CartItemResponseDto findById(Long cartId, User user) {
        // 사용자 정보 가져오기
        User userId = userRepository.findByIdOrElseThrow(user.getUserId());

        CartItem cartItem = cartItemRepository.findByIdOrElseThrow(cartId);

        //사용자의 카트인지 확인
        EntityValidator.validateUserOwnership(userId.getUserId(), cartItem.getUser().getUserId());

        List<ProductOption> productOptions = productOptionRepository.findProductOptionByProductId(cartItem.getProduct().getId());

        return CartItemResponseDto.toDto(cartItem, productOptions);
    }

    @Override
    public Page<CartItemResponseDto> findByAll(User user, CartItemDateRequestDto requestDto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cartItemRepository.findByAllCartItem(user, requestDto, pageable);
    }


    @Transactional
    @Override
    public CartItemResponseDto update(Long cartId, User user, Integer quantity) {
        User userId = userRepository.findByIdOrElseThrow(user.getUserId());

        CartItem cartItem = cartItemRepository.findByIdOrElseThrow(cartId);

        EntityValidator.validateUserOwnership(userId.getUserId(), cartItem.getUser().getUserId());
        cartItem.updateCartItem(quantity);


        // 상품 옵션 조회
        List<ProductOption> productOptions = productOptionRepository.findProductOptionByProductId(cartItem.getProduct().getId());

        return CartItemResponseDto.toDto(cartItem, productOptions);
    }


    @Transactional
    @Override
    public void delete(Long cartId, User user) {
        User userId = userRepository.findByIdOrElseThrow(user.getUserId());

        CartItem cartItem = cartItemRepository.findByIdOrElseThrow(cartId);

        EntityValidator.validateUserOwnership(userId.getUserId(), cartItem.getUser().getUserId());

        cartItemRepository.delete(cartItem);
    }
}