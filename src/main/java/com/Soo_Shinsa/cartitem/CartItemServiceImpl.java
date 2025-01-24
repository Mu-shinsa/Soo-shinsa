package com.Soo_Shinsa.cartitem;




import com.Soo_Shinsa.cartitem.dto.CartItemResponseDto;
import com.Soo_Shinsa.product.ProductOptionRepository;
import com.Soo_Shinsa.product.model.ProductOption;
import com.Soo_Shinsa.user.UserRepository;
import com.Soo_Shinsa.user.model.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
    public CartItemResponseDto create(Long optionId, Integer quantity, User user) {
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
    public CartItemResponseDto findById(Long cartId) {

        //카트 아이템 찾아옴
        CartItem savedCart = findByIdOrElseThrow(cartId);
        //저장
        return CartItemResponseDto.toDto(savedCart);
    }
    //유저의 카트들을 다 가져옴
    @Transactional(readOnly = true)
    @Override
    public List<CartItemResponseDto> findByAll(User user) {
        //로그인 유저의 카트목록들을 다 가져옴
        List<CartItem> allCartItem = cartItemRepository.findAllByUserUserId(user.getUserId());
        //dto 저장
        return allCartItem.stream().map(CartItemResponseDto::toDto).toList();
    }

    //카트 수정
    @Transactional
    @Override
    public CartItemResponseDto update(Long cartId,Integer quantity,User user) {
        CartItem findCart = findByIdOrElseThrow(cartId);

        checkUser(user, findCart);
        //카트 아이템 검색
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
    public CartItemResponseDto delete(Long cartId,User user) {

        //카트를 가져옴
        CartItem findCart = findByIdOrElseThrow(cartId);

        checkUser(user,findCart);
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

    private static void checkUser(User user, CartItem cartItem) {
        if (!cartItem.getUser().getUserId().equals(user.getUserId())) {
            throw new SecurityException("수정 또는 삭제할 권한이 없습니다.");
        }
    }
}
