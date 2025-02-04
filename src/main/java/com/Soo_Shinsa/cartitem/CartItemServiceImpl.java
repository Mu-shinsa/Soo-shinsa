package com.Soo_Shinsa.cartitem;

import com.Soo_Shinsa.cartitem.dto.CartItemDateRequestDto;
import com.Soo_Shinsa.cartitem.dto.CartItemRequestDto;
import com.Soo_Shinsa.cartitem.dto.CartItemResponseDto;
import com.Soo_Shinsa.cartitem.model.CartItem;
import com.Soo_Shinsa.cartitem.model.QCartItem;
import com.Soo_Shinsa.product.ProductOptionRepository;
import com.Soo_Shinsa.product.ProductRepository;
import com.Soo_Shinsa.product.model.Product;
import com.Soo_Shinsa.product.model.ProductOption;
import com.Soo_Shinsa.product.model.QProductOption;
import com.Soo_Shinsa.user.UserRepository;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.utils.EntityValidator;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductOptionRepository productOptionRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final JPAQueryFactory queryFactory;

    @Transactional
    @Override
    public CartItemResponseDto create(User user, CartItemRequestDto requestDto) {

        Product product = productRepository.findByIdOrElseThrow(requestDto.getProductId());

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
        QCartItem cartItem = QCartItem.cartItem;
        QProductOption productOption = QProductOption.productOption;

        Pageable pageable = PageRequest.of(page, size);

        // 동적 조건 생성
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cartItem.user.userId.eq(user.getUserId())); // 사용자 ID 조건

        // 생성 날짜 조건 추가
        if (requestDto.getStartDate() != null) {
            builder.and(cartItem.createdAt.goe(Timestamp.valueOf(requestDto.getStartDate().atStartOfDay()))); // 시작 날짜 이상
        }
        if (requestDto.getEndDate() != null) {
            builder.and(cartItem.createdAt.loe(Timestamp.valueOf(requestDto.getEndDate().atTime(23, 59, 59)))); // 종료 날짜 이하
        }

        // QueryDSL로 CartItem 목록 조회
        List<CartItem> cartItems = queryFactory
                .selectFrom(cartItem)
                .where(builder)                     // 동적 조건 적용
                .orderBy(cartItem.createdAt.desc()) // 생성 날짜 기준 내림차순 정렬
                .offset(pageable.getOffset())       // 페이징 시작 위치
                .limit(pageable.getPageSize())      // 페이지 크기
                .fetch();

        // 총 개수 계산
        long total = queryFactory
                .select(cartItem.count())
                .from(cartItem)
                .where(builder)
                .fetch()
                .size();

        // CartItem → CartItemResponseDto 변환
        List<CartItemResponseDto> cartItemResponseDtos = cartItems.stream()
                .map(item -> {
                    // QueryDSL로 ProductOption 조회
                    List<ProductOption> productOptions = queryFactory
                            .selectFrom(productOption)
                            .where(productOption.product.id.eq(item.getProduct().getId())) // Product ID 조건
                            .fetch();

                    return CartItemResponseDto.toDto(item, productOptions); // DTO 변환
                })
                .toList();

        return new PageImpl<>(cartItemResponseDtos, pageable, total);
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